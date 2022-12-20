package ProjektInz.RESTAPI.restApi;

import lombok.Getter;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.LinkedHashMap;

@Getter
public class AllegroAdvertResponse {

    private LinkedHashMap<String, Object> body;
    public AllegroAdvertResponse(ResponseEntity<Object> response) {
        this.body = (LinkedHashMap<String, Object>) response.getBody();
    }

    public ArrayList<LinkedHashMap<String, Object>> getOffers(AllegroAdvertResponse allegroAdvertResponse){
        LinkedHashMap<String, Object> offers = allegroAdvertResponse.getBody();
        return (ArrayList<LinkedHashMap<String, Object>>) offers.get("offers");
    }
}
