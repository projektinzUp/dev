package ProjektInz.RESTAPI.Service.Olx;

import ProjektInz.RESTAPI.repository.OlxAdvertsRepository;
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
public class OlxAdvertService {
    @Autowired
    @Qualifier("simpleRestTemplate")
    private RestTemplate restTemplate;
    @Value("${olx.host}")
    private String olxHost;

    @Autowired
    OlxAdvertsRepository olxAdvertsRepository;

    public HttpEntity<String> createHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(OlxAuthorizationCodeToken.accessToken);
        httpHeaders.set("Version", "Version:2.0");
        List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.ALL);
        httpHeaders.setAccept(mediaTypes);

        return new HttpEntity<>(httpHeaders);
    }
    public Object getAdverts() throws Exception {
        try {
            HttpEntity<String> entity = createHeaders();
            UriComponentsBuilder builder;
            builder = UriComponentsBuilder.fromUriString(olxHost + "api/partner/adverts");
            URI requestUri = builder.build(true).toUri();
            ResponseEntity<Object> advert = restTemplate.exchange(requestUri, HttpMethod.GET, entity, Object.class);
            OlxAdvertResponse olxAdvertResponse = new OlxAdvertResponse(advert);
            return olxAdvertResponse.getValues(olxAdvertResponse);
        } catch (Exception exception) {
            log.error("Error occured when downloading adverts, message " + exception.getMessage());
            throw new Exception("Error occured when downloading adverts, message " + exception.getMessage());
        }
    }

    public List<OlxAdvert> findAllAdverts() {
        return (List<OlxAdvert>) olxAdvertsRepository.findAll();
    }

    public List<OlxAdvert> getByKeyword(String keyword) {
        return olxAdvertsRepository.findByKeyword(keyword.toLowerCase(Locale.ROOT));
    }

    public List<OlxAdvert> sortByTitleDesc() {
        return olxAdvertsRepository.sortByTitleDesc();
    }

    public List<OlxAdvert> sortByTitleAsc() {
        return olxAdvertsRepository.sortByTitleAsc();
    }

    public List<OlxAdvert> sortByPriceDesc() {
        return olxAdvertsRepository.sortByPriceDesc();
    }

    public List<OlxAdvert> sortByPriceAsc() {
        return olxAdvertsRepository.sortByPriceAsc();
    }
}
