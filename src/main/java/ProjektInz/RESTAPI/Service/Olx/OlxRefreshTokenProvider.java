package ProjektInz.RESTAPI.Service.Olx;

import ProjektInz.RESTAPI.restApi.Olx.OlxAuthorizationCodeToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Objects;

@Slf4j
@Service
public class OlxRefreshTokenProvider extends AbstractCreateRequest {

    @Autowired
    @Qualifier("simpleRestTemplate")
    private RestTemplate restTemplate;
    @Value("${olx.clientSecret}")
    private String clientSecret;
    @Value("${olx.clientId}")
    private String clientId;
    @Value("${olx.host}")
    private String olxHost;

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
            log.error("Error occurred when downloading bearerToken, message " + exception.getMessage());
            throw new Exception("Error occurred when downloading bearerToken, message " + exception.getMessage());
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
