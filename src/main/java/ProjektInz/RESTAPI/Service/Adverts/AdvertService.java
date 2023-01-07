package ProjektInz.RESTAPI.Service.Adverts;


import ProjektInz.RESTAPI.repository.AdvertsRepository;
import ProjektInz.RESTAPI.repository.OlxAdvertsRepository;
import ProjektInz.RESTAPI.restApi.Allegro.AllegroAdvert;
import ProjektInz.RESTAPI.restApi.Olx.OlxAdvert;
import ProjektInz.RESTAPI.restApi.Olx.OlxAdvertResponse;
import ProjektInz.RESTAPI.restApi.Olx.OlxAuthorizationCodeToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
@Slf4j
public class AdvertService {
    @Autowired
    @Qualifier("simpleRestTemplate")
    private RestTemplate restTemplate;

    @Autowired
    AdvertsRepository advertsRepository;


    public List<AllegroAdvert> findAllAdverts() {
        return (List<AllegroAdvert>) advertsRepository.findAllAdverts();
    }

    public List<AllegroAdvert> getByKeyword(String keyword) {
        return advertsRepository.findByKeyword(keyword.toLowerCase(Locale.ROOT));
    }

    public List<AllegroAdvert> sortByTitleDesc() {
        return advertsRepository.sortByTitleDesc();
    }

    public List<AllegroAdvert> sortByTitleAsc() {
        return advertsRepository.sortByTitleAsc();
    }

    public List<AllegroAdvert> sortByPriceDesc() {
        return advertsRepository.sortByPriceDesc();
    }

    public List<AllegroAdvert> sortByPriceAsc() {
        return advertsRepository.sortByPriceAsc();
    }
}
