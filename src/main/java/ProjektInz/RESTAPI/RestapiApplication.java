package ProjektInz.RESTAPI;

import ProjektInz.RESTAPI.Service.Allegro.AllegroAdvertService;
import ProjektInz.RESTAPI.Service.Allegro.AllegroAdvertsProvider;
import ProjektInz.RESTAPI.Service.Allegro.AllegroAuthorizationCodeTokenProvider;
import ProjektInz.RESTAPI.Service.Allegro.AllegroTokenProvider;
import ProjektInz.RESTAPI.Service.DatabaseSupport;
import ProjektInz.RESTAPI.Service.Olx.OlxAccessTokenProvider;
import ProjektInz.RESTAPI.Service.Olx.OlxAdvertsProvider;
import ProjektInz.RESTAPI.Service.Olx.OlxAuthorizationCodeTokenProvider;
import ProjektInz.RESTAPI.restApi.Olx.OlxAuthorizationCodeToken;
import ProjektInz.RESTAPI.restApi.Olx.OlxToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;


@SpringBootApplication
@Slf4j
public class RestapiApplication {
    @Autowired
    private OlxAccessTokenProvider olxAccessTokenProvider;
    @Autowired
    private OlxAuthorizationCodeTokenProvider olxAuthorizationCodeTokenProvider;
    @Autowired
    private OlxAdvertsProvider olxAdvertsProvider;
    @Autowired
    private DatabaseInit databaseInit;
    @Autowired
    private AllegroTokenProvider allegroTokenProvider;
    @Autowired
    private AllegroAuthorizationCodeTokenProvider allegroAuthorizationCodeTokenProvider;
    @Autowired
    private DatabaseSupport databaseSupport;
    @Autowired
    private AllegroAdvertService allegroAdvertService;
    @Autowired
    private AllegroAdvertsProvider allegroAdvertsProvider;

    public static void main(String[] args) {

        SpringApplication.run(RestapiApplication.class, args);
    }



    @EventListener(ApplicationReadyEvent.class)
    public void applicationStart() {
        try {
            databaseInit.initializeDatabase();
            log.info("Database initialized");
            databaseSupport.codesToRemove();
            OlxToken.accessToken = olxAccessTokenProvider.getOlxBearerToken();
            OlxAuthorizationCodeToken.accessToken = olxAuthorizationCodeTokenProvider.getOlxAuthenticationToken();
            allegroTokenProvider.getAllegroToken();
            allegroAuthorizationCodeTokenProvider.getCode();
            olxAdvertsProvider.createAdvertObject();
            Thread.sleep(2000);
            allegroAuthorizationCodeTokenProvider.getAllegroAuthorizationCodeToken();
            allegroAdvertService.getAdverts();
            allegroAdvertsProvider.createAdvertObject();

        } catch (Exception e) {
            log.error("Error occurred when application initialized, message: " + e.getMessage());
        }
    }
}
