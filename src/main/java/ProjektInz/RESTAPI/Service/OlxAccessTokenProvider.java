package ProjektInz.RESTAPI.Service;

import ProjektInz.RESTAPI.restApi.OlxToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
           HttpEntity<String> entity = createRequestEntity();
           UriComponentsBuilder builder;
           builder = UriComponentsBuilder.fromUriString(olxHost+"api/open/oauth/token");
           URI requestUri = builder.build(true).toUri();
           HttpEntity<OlxToken> token = restTemplate.exchange(requestUri,HttpMethod.POST,entity,OlxToken.class);
           return Objects.requireNonNull(token.getBody()).getAccess_token();
          //  return Objects.requireNonNull(token.getBody().getAccess_token());
        }catch (Exception exception)
        {
            log.error("Error occured when downloading bearerToken, message "+exception.getMessage());
            throw new Exception("Error occured when downloading bearerToken, message "+exception.getMessage());
        }
        }
        private HttpEntity<String> createRequestEntity()
        {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            List<MediaType> mediaTypes = new ArrayList<>();
            mediaTypes.add(MediaType.TEXT_HTML);
            httpHeaders.setAccept(mediaTypes);
            String urlEncoded = convertMapToUrlEncoded(createBodyProperties());

            return new HttpEntity<>(urlEncoded,httpHeaders);
        }

        private MultiValueMap<String,String > createBodyProperties()
        {
            MultiValueMap<String,String> map = new LinkedMultiValueMap<>();
            map.add("grant_type", this.grantType);
            map.add("scope", this.scope);
            map.add("client_id", this.clientId);
            map.add("client_secret", this.clientSecret);
            return map;
        }
        private String convertMapToUrlEncoded(MultiValueMap<String,String> properties)
        {
            String encodedURL = properties.keySet().stream().map(key -> {
                try{
                    return key + "=" + URLEncoder.encode(String.valueOf(properties.get(key)),StandardCharsets.UTF_8.toString());
                }catch (UnsupportedEncodingException e)
                {
                    throw new RuntimeException(e);
                }
            }).collect(Collectors.joining("&"));
            encodedURL = encodedURL.replace("%5B","").replace("%5D","").replace("<","").replace("+","%20");
            return encodedURL;
        }



}
