package ProjektInz.RESTAPI.restApi;

import lombok.Getter;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Getter
public class AdvertResponse {
    private List<Advert> body;

    public AdvertResponse(ResponseEntity<Object>response) {
        this.body = (List<Advert>)response.getBody();
    }
}
