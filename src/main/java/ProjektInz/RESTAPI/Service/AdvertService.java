package ProjektInz.RESTAPI.Service;

import ProjektInz.RESTAPI.repository.AdvertsRepository;
import ProjektInz.RESTAPI.restApi.Advert;
import ProjektInz.RESTAPI.restApi.AdvertResponse;
import ProjektInz.RESTAPI.restApi.OlxAuthorizationCodeToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class AdvertService  {
    private final RestTemplate restTemplate;
    @Value("${olx.host}")
    private String olxHost;

    @Autowired
    AdvertsRepository advertsRepository;

    public AdvertService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
        List<HttpMessageConverter<?>> messageConverterList = new ArrayList<>();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
        messageConverterList.add(converter);
        this.restTemplate.setMessageConverters(messageConverterList);
        this.restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
    }

    public HttpEntity<String> createHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(OlxAuthorizationCodeToken.accessToken);
        httpHeaders.set("Version","Version:2.0");
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
            AdvertResponse advertResponse = new AdvertResponse(advert);
            return advertResponse.getValues(advertResponse);
        } catch (Exception exception) {
            log.error("Error occured when downloading adverts, message " + exception.getMessage());
            throw new Exception("Error occured when downloading adverts, message " + exception.getMessage());
        }
    }

    public List<Advert> findAllAdverts(){
        return (List<Advert>) advertsRepository.findAll();
    }
}
