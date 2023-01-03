package ProjektInz.RESTAPI.Service.Olx;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractCreateRequest {

    public static void setAutomaticRedirect(RestTemplate restTemplate) {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        HttpClient httpClient = HttpClientBuilder.create()
                .setRedirectStrategy(new LaxRedirectStrategy()).build();
        factory.setHttpClient(httpClient);
        restTemplate.setRequestFactory(factory);
    }

    public String convertMapToUrlEncoded(MultiValueMap<String, String> properties) {
        String encodedURL = properties.keySet().stream().map(key -> {
            try {
                return key + "=" + URLEncoder.encode(String.valueOf(properties.get(key)), StandardCharsets.UTF_8.toString());
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.joining("&"));
        encodedURL = encodedURL.replace("%5B", "").replace("%5D", "").replace("<", "").replace("+", "%20");
        return encodedURL;
    }

    abstract MultiValueMap<String, String> createBodyProperties();

    public HttpEntity<String> createRequestEntity() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.TEXT_HTML);
        httpHeaders.setAccept(mediaTypes);
        String urlEncoded = convertMapToUrlEncoded(createBodyProperties());

        return new HttpEntity<>(urlEncoded, httpHeaders);
    }
}
