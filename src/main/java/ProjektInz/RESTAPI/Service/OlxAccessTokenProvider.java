package ProjektInz.RESTAPI.Service;

import ProjektInz.RESTAPI.restApi.OlxToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
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

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OlxAccessTokenProvider extends AbstractCreateRequest{

    private final RestTemplate restTemplate;
    @Value("${olx.host}")
    private String olxHost;
    @Value("${olx.clientId}")
    private String clientId;
    @Value("${olx.grantType}")
    private String grantType;
    @Value("${olx.scope}")
    private String scope;
    @Value("${olx.clientSecret}")
    private String clientSecret;

    public OlxAccessTokenProvider(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
        List<HttpMessageConverter<?>> messageConverterList = new ArrayList<>();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
        messageConverterList.add(converter);
        this.restTemplate.setMessageConverters(messageConverterList);
        this.restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
    }

    public String getOlxBearerToken() throws Exception {
        try {
            HttpEntity<String> entity = createRequestEntity();
            UriComponentsBuilder builder;
            builder = UriComponentsBuilder.fromUriString(olxHost + "api/open/oauth/token");
            URI requestUri = builder.build(true).toUri();
            HttpEntity<OlxToken> token = restTemplate.exchange(requestUri, HttpMethod.POST, entity, OlxToken.class);
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
        return map;
    }

}
