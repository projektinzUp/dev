package ProjektInz.RESTAPI.restApi;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Advert {
    private int id;
    private String status;
    private String url;
    private String created_at;
    private String activated_at;
    private String valid_to;
    private String title;
    private String description;
    private int category_id;
    private String advertiser_type;
    private int external_id;
    private String external_url;
    private Map<String, Object> contact;
    private Map<String, Object> location;
    private List<String> images;
    private Map<String, Object> price;
    private double salary;
    private List<Map<String,Object>> attributes;
    private String courier;
}
