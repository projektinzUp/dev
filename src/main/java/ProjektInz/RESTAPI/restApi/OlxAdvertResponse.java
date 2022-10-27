package ProjektInz.RESTAPI.restApi;

import lombok.Getter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.LinkedHashMap;

@Getter
public class OlxAdvertResponse {
    private Integer status;
    private HttpHeaders headers;
    private LinkedHashMap<String, ArrayList<LinkedHashMap<String, ArrayList<LinkedHashMap<String, Object>>>>> body;

    public OlxAdvertResponse(ResponseEntity<Object> response) {
        this.body = (LinkedHashMap<String, ArrayList<LinkedHashMap<String, ArrayList<LinkedHashMap<String, Object>>>>>) response.getBody();
    }

    public ArrayList<LinkedHashMap<String, ArrayList<LinkedHashMap<String, Object>>>> getValues(OlxAdvertResponse olxAdvertResponse) {
        LinkedHashMap<String, ArrayList<LinkedHashMap<String, ArrayList<LinkedHashMap<String, Object>>>>> bodyResponse = olxAdvertResponse.getBody();
        assert bodyResponse != null;
        return bodyResponse.get("data");
    }
}
