package ProjektInz.RESTAPI.restApi;

import lombok.Getter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Getter
public class AdvertResponse {
    private Integer status;
    private HttpHeaders headers;
    private LinkedHashMap<String,ArrayList<LinkedHashMap<String,ArrayList<LinkedHashMap<String,Object>>>>> body;
    public AdvertResponse(ResponseEntity<Object>response) {
        this.body = (LinkedHashMap<String, ArrayList<LinkedHashMap<String, ArrayList<LinkedHashMap<String, Object>>>>>) response.getBody();
    }
}
