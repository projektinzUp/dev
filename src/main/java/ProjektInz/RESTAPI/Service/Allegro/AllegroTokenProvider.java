package ProjektInz.RESTAPI.Service.Allegro;

import ProjektInz.RESTAPI.restApi.Allegro.AllegroToken;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
@Slf4j
public class AllegroTokenProvider {
    @Value("${olx.grantType}")
    private String grantType;
    @Value("${allegro.client_id}")
    private String clientId;
    @Value("${allegro.client_secret}")
    private String clientSecret;
    @Autowired
    @Qualifier("simpleRestTemplate")
    private RestTemplate restTemplate;

    public String getAllegroToken() throws Exception {
        try {
            log.info("Starting to download Allegro access token");
            HttpEntity<String> entity = createHeaders();
            UriComponentsBuilder builder;
            builder = UriComponentsBuilder.fromUriString("https://allegro.pl.allegrosandbox.pl/auth/oauth/token").queryParam("grant_type", this.grantType);
            URI requestUri = builder.build(true).toUri();
            ResponseEntity<AllegroToken> advert = restTemplate.exchange(requestUri, HttpMethod.POST, entity, AllegroToken.class);
            log.info("Allegro access token downloaded");
            return advert.getBody().getAccess_token();
        } catch (Exception exception) {
            log.error("Error occurred when downloading Allegro access token, message: " + exception.getMessage());
            throw new Exception("Error occurred when downloading Allegro access token, message: " + exception.getMessage());
        }
    }

    private HttpEntity<String> createHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        String auth = this.clientId + ":" + this.clientSecret;
        httpHeaders.setBasicAuth(Base64.getEncoder().encodeToString(auth.getBytes()));
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.ALL);
        httpHeaders.setAccept(mediaTypes);

        return new HttpEntity<>(httpHeaders);
    }

    private void setAutomaticRedirect(RestTemplate restTemplate) {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        HttpClient httpClient = HttpClientBuilder.create()
                .setRedirectStrategy(new LaxRedirectStrategy()).build();
        factory.setHttpClient(httpClient);
        restTemplate.setRequestFactory(factory);
    }
}
