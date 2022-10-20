package ProjektInz.RESTAPI;

import ProjektInz.RESTAPI.Service.AdvertsProvider;
import ProjektInz.RESTAPI.Service.OlxAccessTokenProvider;
import ProjektInz.RESTAPI.Service.OlxAuthorizationCodeTokenProvider;
import ProjektInz.RESTAPI.repository.AdvertsRepository;
import ProjektInz.RESTAPI.restApi.Advert;
import ProjektInz.RESTAPI.restApi.OlxAuthorizationCodeToken;
import ProjektInz.RESTAPI.restApi.OlxToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@SpringBootApplication
public class RestapiApplication {
    @Autowired
    private OlxAccessTokenProvider olxAccessTokenProvider;
    @Autowired
    private OlxAuthorizationCodeTokenProvider olxAuthorizationCodeTokenProvider;
    @Autowired
    private AdvertsProvider advertsProvider;
    @Autowired
    private DatabaseInit databaseInit;


    public static void main(String[] args) {

        SpringApplication.run(RestapiApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @EventListener(ApplicationReadyEvent.class)
    public void applicationStart() {
        try {
            databaseInit.initializeDatabase();
            OlxToken.accessToken = olxAccessTokenProvider.getOlxBearerToken();
            System.out.println(OlxToken.accessToken);
            OlxAuthorizationCodeToken.accessToken = olxAuthorizationCodeTokenProvider.getOlxAuthenticationToken();
            System.out.println(OlxAuthorizationCodeToken.accessToken);
            System.out.println(OlxAuthorizationCodeToken.refreshToken);
            List<Advert> advertList = advertsProvider.createAdvertObject();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
