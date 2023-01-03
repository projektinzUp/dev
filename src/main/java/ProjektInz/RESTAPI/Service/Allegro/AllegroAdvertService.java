package ProjektInz.RESTAPI.Service.Allegro;

import ProjektInz.RESTAPI.repository.AllegroAdvertsRepository;
import ProjektInz.RESTAPI.restApi.Allegro.AllegroAdvert;
import ProjektInz.RESTAPI.restApi.Allegro.AllegroAdvertResponse;
import ProjektInz.RESTAPI.restApi.Allegro.AllegroAuthorizationCodeToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Locale;

@Service
@Slf4j
public class AllegroAdvertService {
    @Autowired
    @Qualifier("simpleRestTemplate")
    private RestTemplate restTemplate;

    @Autowired
    AllegroAdvertsRepository allegroAdvertsRepository;

    private HttpEntity<String> createHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(AllegroAuthorizationCodeToken.accessToken);
        httpHeaders.set("Accept", "application/vnd.allegro.public.v1+json");

        return new HttpEntity<>(httpHeaders);
    }

    public Object getAdverts() throws Exception {
        try {
            HttpEntity<String> entity = createHeaders();
            UriComponentsBuilder builder;
            builder = UriComponentsBuilder.fromUriString("https://api.allegro.pl.allegrosandbox.pl/sale/offers");
            URI requestUri = builder.build(true).toUri();
            ResponseEntity<Object> advert = restTemplate.exchange(requestUri, HttpMethod.GET, entity, Object.class);
            AllegroAdvertResponse allegroAdvertResponse = new AllegroAdvertResponse(advert);
            return allegroAdvertResponse.getOffers(allegroAdvertResponse);
        } catch (Exception exception) {
            log.error("Error occured when downloading adverts, message " + exception.getMessage());
            throw new Exception("Error occured when downloading adverts, message " + exception.getMessage());
        }
    }

    public List<AllegroAdvert> findAllAdverts() {
        return (List<AllegroAdvert>) allegroAdvertsRepository.findAll();
    }

    public List<AllegroAdvert> getByKeyword(String keyword) {
        return allegroAdvertsRepository.findByKeyword(keyword.toLowerCase(Locale.ROOT));
    }

    public List<AllegroAdvert> sortByTitleDesc() {
        return allegroAdvertsRepository.sortByTitleDesc();
    }

    public List<AllegroAdvert> sortByTitleAsc() {
        return allegroAdvertsRepository.sortByTitleAsc();
    }

    public List<AllegroAdvert> sortByPriceDesc() {
        return allegroAdvertsRepository.sortByPriceDesc();
    }

    public List<AllegroAdvert> sortByPriceAsc() {
        return allegroAdvertsRepository.sortByPriceAsc();
    }
}
