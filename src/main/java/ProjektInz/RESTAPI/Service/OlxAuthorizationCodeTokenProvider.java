package ProjektInz.RESTAPI.Service;

import ProjektInz.RESTAPI.Handlers.AllegroHandler;
import ProjektInz.RESTAPI.Handlers.OlxHandler;
import ProjektInz.RESTAPI.repository.OlxCodeEntityRepository;
import ProjektInz.RESTAPI.restApi.OlxAuthorizationCodeToken;
import com.sun.net.httpserver.HttpServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.awt.*;
import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class OlxAuthorizationCodeTokenProvider extends AbstractCreateRequest implements GetCode {
    private final RestTemplate restTemplate;
    @Value("${olx.host}")
    private String olxHost;
    @Value("${olx.clientId}")
    private String clientId;
    @Value("${olx.grantTypeCode}")
    private String grantType;
    @Value("${olx.scope}")
    private String scope;
    @Value("${olx.clientSecret}")
    private String clientSecret;
    @Value("${olx.redirect_uri}")
    private String redirect_uri;

    @Autowired
    private OlxCodeEntityRepository olxCodeEntityRepository;

    public OlxAuthorizationCodeTokenProvider(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
        List<HttpMessageConverter<?>> messageConverterList = new ArrayList<>();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
        messageConverterList.add(converter);
        this.restTemplate.setMessageConverters(messageConverterList);
        this.restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
    }

    public String getOlxAuthenticationToken() throws Exception {
        try {
            HttpEntity<String> entity = createRequestEntity();
            UriComponentsBuilder builder;
            builder = UriComponentsBuilder.fromUriString(olxHost + "api/open/oauth/token");
            URI requestUri = builder.build(true).toUri();
            HttpEntity<OlxAuthorizationCodeToken> token = restTemplate.exchange(requestUri, HttpMethod.POST, entity, OlxAuthorizationCodeToken.class);
            OlxAuthorizationCodeToken.refreshToken = Objects.requireNonNull(token.getBody()).getRefresh_token();
            return Objects.requireNonNull(token.getBody()).getAccess_token();
        } catch (Exception exception) {
            log.error("Error occured when downloading bearerToken, message " + exception.getMessage());
            throw new Exception("Error occured when downloading bearerToken, message " + exception.getMessage());
        }
    }

    @Override
    public MultiValueMap<String, String> createBodyProperties() {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", this.grantType);
        map.add("scope", this.scope);
        map.add("client_id", this.clientId);
        map.add("client_secret", this.clientSecret);
        map.add("code", olxCodeEntityRepository.getCode());
        map.add("redirect_uri", this.redirect_uri);
        return map;
    }


    @Override
    public void getCode() throws Exception {
        UriComponentsBuilder builder;
        builder = UriComponentsBuilder.fromUriString("https://www.olx.pl/oauth/authorize/").queryParam("client_id", this.clientId).queryParam("response_type", "code").queryParam("scope", this.scope).queryParam("redirect_uri", this.redirect_uri);
        URI requestUri = builder.build(true).toUri();
        System.setProperty("java.awt.headless", "false");
        Desktop desktop = Desktop.getDesktop();
        desktop.browse(requestUri);

        HttpServer server = HttpServer.create(new InetSocketAddress(8001), 0);
        server.createContext("", new OlxHandler(olxCodeEntityRepository));
        server.setExecutor(null);
        server.start();
    }
}
