package ProjektInz.RESTAPI.Service;

import ProjektInz.RESTAPI.restApi.OlxAuthorizationCodeToken;
import lombok.extern.slf4j.Slf4j;
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

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class OlxRefreshTokenProvider extends AbstractCreateRequest {

    private final RestTemplate restTemplate;
    @Value("${olx.clientSecret}")
    private String clientSecret;
    @Value("${olx.clientId}")
    private String clientId;
    @Value("${olx.host}")
    private String olxHost;

    public OlxRefreshTokenProvider(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
        List<HttpMessageConverter<?>> messageConverterList = new ArrayList<>();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
        messageConverterList.add(converter);
        this.restTemplate.setMessageConverters(messageConverterList);
        this.restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
    }

    public String getOlxRefreshToken() throws Exception {
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
    MultiValueMap<String, String> createBodyProperties() {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", "refresh_token");
        map.add("client_id", this.clientId);
        map.add("client_secret", this.clientSecret);
        map.add("refresh_token", OlxAuthorizationCodeToken.refreshToken);
        return map;
    }
}
