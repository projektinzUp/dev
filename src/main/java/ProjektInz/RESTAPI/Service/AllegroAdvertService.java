package ProjektInz.RESTAPI.Service;

import ProjektInz.RESTAPI.repository.AllegroAdvertsRepository;
import ProjektInz.RESTAPI.repository.OlxAdvertsRepository;
import ProjektInz.RESTAPI.restApi.AllegroAdvert;
import ProjektInz.RESTAPI.restApi.AllegroAdvertResponse;
import ProjektInz.RESTAPI.restApi.AllegroAuthorizationCodeToken;
import ProjektInz.RESTAPI.restApi.OlxAdvert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.Collections;
import java.util.List;
import java.util.Locale;

@Service
@Slf4j
public class AllegroAdvertService {
    private final RestTemplate restTemplate;

    @Autowired
    AllegroAdvertsRepository allegroAdvertsRepository;

    public AllegroAdvertService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
        List<HttpMessageConverter<?>> messageConverterList = new ArrayList<>();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
        messageConverterList.add(converter);
        this.restTemplate.setMessageConverters(messageConverterList);
        this.restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
    }

    private HttpEntity<String> createHeaders(){
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
