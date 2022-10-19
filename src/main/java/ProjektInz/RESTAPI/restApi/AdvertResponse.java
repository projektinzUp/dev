package ProjektInz.RESTAPI.restApi;

import lombok.Getter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.LinkedHashMap;

@Getter
public class AdvertResponse {
    private Integer status;
    private HttpHeaders headers;
    private LinkedHashMap<String,ArrayList<LinkedHashMap<String,ArrayList<LinkedHashMap<String,Object>>>>> body;
    public AdvertResponse(ResponseEntity<Object>response) {
        this.body = (LinkedHashMap<String, ArrayList<LinkedHashMap<String, ArrayList<LinkedHashMap<String, Object>>>>>) response.getBody();
    }

    public ArrayList<LinkedHashMap<String, ArrayList<LinkedHashMap<String, Object>>>> getValues(AdvertResponse advertResponse)
    {
        LinkedHashMap<String,ArrayList<LinkedHashMap<String,ArrayList<LinkedHashMap<String,Object>>>>> bodyResponse = advertResponse.getBody();
        assert bodyResponse != null;
        return bodyResponse.get("data");
    }
}
