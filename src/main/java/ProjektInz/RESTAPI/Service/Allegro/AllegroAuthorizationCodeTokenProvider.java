package ProjektInz.RESTAPI.Service.Allegro;

import ProjektInz.RESTAPI.Handlers.AllegroHandler;
import ProjektInz.RESTAPI.Service.GetCode;
import ProjektInz.RESTAPI.repository.CodeEntityRepository;
import ProjektInz.RESTAPI.restApi.Allegro.AllegroAuthorizationCodeToken;
import com.sun.net.httpserver.HttpServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.awt.*;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class AllegroAuthorizationCodeTokenProvider implements GetCode {
    @Value("${allegro.client_id}")
    private String client_id;
    @Value("${allegro.client_secret}")
    private String client_secret;
    @Value("${allegro.redirect_uri}")
    private String redirect_uri;
    @Autowired
    private CodeEntityRepository codeEntityRepository;
    @Autowired
    @Qualifier("simpleRestTemplate")
    private RestTemplate restTemplate;

    @Override
    public void getCode() throws Exception {
        UriComponentsBuilder builder;
        builder = UriComponentsBuilder.fromUriString("https://allegro.pl.allegrosandbox.pl/auth/oauth/authorize").queryParam("response_type", "code").queryParam("client_id", this.client_id).queryParam("redirect_uri", this.redirect_uri);
        URI requestUri = builder.build(true).toUri();
        System.setProperty("java.awt.headless", "false");
        Desktop desktop = Desktop.getDesktop();
        desktop.browse(requestUri);

        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/test", new AllegroHandler(codeEntityRepository));
        server.setExecutor(null);
        server.start();
    }

    public void getAllegroAuthorizationCodeToken() throws Exception {
        try {
            HttpEntity<String> entity = createHeaders();
            UriComponentsBuilder builder;
            String code = codeEntityRepository.getCode();
            builder = UriComponentsBuilder.fromUriString("https://allegro.pl.allegrosandbox.pl/auth/oauth/token").queryParam("grant_type", "authorization_code").queryParam("code", code).queryParam("redirect_uri", this.redirect_uri);
            URI requestUri = builder.build(true).toUri();
            ResponseEntity<AllegroAuthorizationCodeToken> advert = restTemplate.exchange(requestUri, HttpMethod.POST, entity, AllegroAuthorizationCodeToken.class);
            AllegroAuthorizationCodeToken.accessToken = Objects.requireNonNull(advert.getBody()).getAccess_token();
        } catch (Exception exception) {
            log.error("Error occured when downloading Allegro access token, message " + exception.getMessage());
            throw new Exception("Error occured when downloading Allegro access token, message " + exception.getMessage());
        }
    }

    private HttpEntity<String> createHeaders() {
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
