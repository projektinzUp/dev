package ProjektInz.RESTAPI.Service.Olx;

import ProjektInz.RESTAPI.restApi.Olx.OlxToken;
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
public class OlxAccessTokenProvider extends AbstractCreateRequest {

    @Autowired
    @Qualifier("simpleRestTemplate")
    private RestTemplate restTemplate;
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

    public String getOlxBearerToken() throws Exception {
        try {
            log.info("Starting to download olx access token");
            HttpEntity<String> entity = createRequestEntity();
            UriComponentsBuilder builder;
            builder = UriComponentsBuilder.fromUriString(olxHost + "api/open/oauth/token");
            URI requestUri = builder.build(true).toUri();
            HttpEntity<OlxToken> token = restTemplate.exchange(requestUri, HttpMethod.POST, entity, OlxToken.class);
            log.info("Olx access token downloaded");
            return Objects.requireNonNull(token.getBody()).getAccess_token();
        } catch (Exception exception) {
            log.error("Error occurred when downloading olx access token, message: " + exception.getMessage());
            throw new Exception("Error occurred when downloading olx access token, message: " + exception.getMessage());
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
