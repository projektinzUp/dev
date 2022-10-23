package ProjektInz.RESTAPI.Service;

import ProjektInz.RESTAPI.restApi.AdvertResponse;
import ProjektInz.RESTAPI.restApi.AllegroToken;
import ProjektInz.RESTAPI.restApi.OlxAuthorizationCodeToken;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
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
    private final RestTemplate restTemplate;

    public AllegroTokenProvider(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
        List<HttpMessageConverter<?>> messageConverterList = new ArrayList<>();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
        messageConverterList.add(converter);
        this.restTemplate.setMessageConverters(messageConverterList);
        this.restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        setAutomaticRedirect(this.restTemplate);
    }


    public String getAllegroToken() throws Exception {
        try {
            HttpEntity<String> entity = createHeaders();
            UriComponentsBuilder builder;
            builder = UriComponentsBuilder.fromUriString("https://www.allegro.pl/auth/oauth/token").queryParam("grant_type", this.grantType);
            URI requestUri = builder.build(true).toUri();
            ResponseEntity<AllegroToken> advert = restTemplate.exchange(requestUri, HttpMethod.POST, entity, AllegroToken.class);
            return advert.getBody().getAccess_token();
        } catch (Exception exception) {
            log.error("Error occured when downloading Allegro access token, message " + exception.getMessage());
            throw new Exception("Error occured when downloading Allegro access token, message " + exception.getMessage());
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
