package ProjektInz.RESTAPI.Service;

import ProjektInz.RESTAPI.restApi.OlxToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.cbor.MappingJackson2CborHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class OlxAccessTokenProvider {

    private final RestTemplate restTemplate;
    @Value("${olx.host}")
    private String olxHost;
    @Value("${olx.clientId}")
    private String clientId;
    @Value("${olx.grantType}")
    private String grantType;
    @Value("${olx.scope}")
    private String scope;
    @Value("${olx.clientSecret}")
    private String clientSecret;

//    public OlxAccessTokenProvider(RestTemplate restTemplate) {
//        this.restTemplate = restTemplate;
//     List<HttpMessageConverter<?>> messageConverterList = new ArrayList<>();
//        MappingJackson2CborHttpMessageConverter converter = new MappingJackson2CborHttpMessageConverter();
//        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
//        messageConverterList.add(converter);
//       this.restTemplate.setMessageConverters(messageConverterList);
//        this.restTemplate.getMessageConverters().add(0,new StringHttpMessageConverter(StandardCharsets.UTF_8));
//    }
    public OlxAccessTokenProvider(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
        List<HttpMessageConverter<?>> messageConverterList = new ArrayList<>();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
        messageConverterList.add(converter);
       this.restTemplate.setMessageConverters(messageConverterList);
        this.restTemplate.getMessageConverters().add(0,new StringHttpMessageConverter(StandardCharsets.UTF_8));
    }

// public OlxAccessTokenProvider(RestTemplate template)
// {
//     this.restTemplate = template;
//     List<HttpMessageConverter<?>> messageConverterList = new ArrayList<>();
//        MappingJackson2CborHttpMessageConverter converter = new MappingJackson2CborHttpMessageConverter();
//        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
//        messageConverterList.add(converter);
//       this.restTemplate.setMessageConverters(messageConverterList);
//        this.restTemplate.getMessageConverters().add(0,new StringHttpMessageConverter(StandardCharsets.UTF_8));
// }

//    @Bean
//    public RestTemplate restTemplate()
//    {
//        RestTemplate restTemplate = new RestTemplate();
//        List<HttpMessageConverter<?>> messageConverterList = new ArrayList<>();
//        MappingJackson2CborHttpMessageConverter converter = new MappingJackson2CborHttpMessageConverter();
//        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
//        messageConverterList.add(converter);
//        restTemplate.setMessageConverters(messageConverterList);
//        restTemplate.getMessageConverters().add(0,new StringHttpMessageConverter(StandardCharsets.UTF_8));
//       return restTemplate;
//    }



    public String getOlxBearerToken() throws Exception
    {
        try {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.TEXT_HTML);
            UriComponentsBuilder builder = null;
            builder = UriComponentsBuilder.fromUriString(olxHost + "api/open/oauth/token")
                    .queryParam("grant_type", this.grantType)
                    .queryParam("scope", "read%20write%20v2")
                    .queryParam("client_id", this.clientId)
                    .queryParam("client_secret", this.clientSecret);
            URI requestUri = builder.build(false).toUri();
           if(requestUri.toString().equals("%2520"))
            log.info("OLX Bearer Token downloaded");
            HttpEntity<Object> token = restTemplate.exchange(requestUri, HttpMethod.POST, new HttpEntity<>(httpHeaders), Object.class);
            System.out.printf("asd");
            return null;
          //  return Objects.requireNonNull(token.getBody().getAccess_token());
        }catch (Exception exception)
        {
            log.error("Error occured when downloading bearerToken, message "+exception.getMessage());
            throw new Exception("Error occured when downloading bearerToken, message "+exception.getMessage());
        }
        }


}
