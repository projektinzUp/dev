package ProjektInz.RESTAPI.Service;

import ProjektInz.RESTAPI.restApi.AllegroAuthorizationCodeToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class AllegroAuthorizationCodeTokenProvider extends AbstractAllegroRequests {

    @Value("${allegro.client_id}")
    private String client_id;
    @Value("${allegro.client_secret}")
    private String client_secret;
    @Value("${allegro.code}")
    private String code;
    @Value("${allegro.redirect_uri}")
    private String redirect_uri;

    private final RestTemplate restTemplate;

    public AllegroAuthorizationCodeTokenProvider(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
        List<HttpMessageConverter<?>> messageConverterList = new ArrayList<>();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
        messageConverterList.add(converter);
        this.restTemplate.setMessageConverters(messageConverterList);
        this.restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        AbstractCreateRequest.setAutomaticRedirect(restTemplate);
    }

    public String getAllegroAuthorizationCodeToken() throws Exception {
        try {
            HttpEntity<String> entity = createHeaders();
            UriComponentsBuilder builder;
            builder = UriComponentsBuilder.fromUriString("https://www.allegro.pl/auth/oauth/token").queryParam("grant_type", "authorization_code").queryParam("code", this.code).queryParam("redirect_uri", this.redirect_uri);
            URI requestUri = builder.build(true).toUri();
            ResponseEntity<AllegroAuthorizationCodeToken> advert = restTemplate.exchange(requestUri, HttpMethod.POST, entity, AllegroAuthorizationCodeToken.class);
            return advert.getBody().getAccess_token();
        } catch (Exception exception) {
            log.error("Error occured when downloading Allegro access token, message " + exception.getMessage());
            throw new Exception("Error occured when downloading Allegro access token, message " + exception.getMessage());
        }
    }

    @Override
    public HttpEntity<String> createHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        String auth = this.client_id + ":" + this.client_secret;
        httpHeaders.setBasicAuth(Base64.getEncoder().encodeToString(auth.getBytes()));
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.ALL);
        httpHeaders.setAccept(mediaTypes);
        return new HttpEntity<>(httpHeaders);
    }


}
