package ProjektInz.RESTAPI.restApi;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class Advert {
    private String id;
    private String status;
    private String url;
    private String created_at;
    private String activated_at;
    private String valid_to;
    private String title;
    private String description;
    private String category_id;
    private String advertiser_type;
    private String external_id;
    private String external_url;
    private Map<String, Object> contact;
    private Map<String, Object> location;
    private List<String> images;
    private Map<String, Object> price;
    private String salary;
    private List<Map<String,Object>> attributes;
    private String courier;


    public Advert(Map<String ,Object> advert)
    {
        this.id = advert.get("id").toString();
        this.status = advert.get("status").toString();
        this.url = advert.get("url").toString();
        this.created_at = advert.get("created_at").toString();
        this.activated_at = advert.get("activated_at").toString();
        this.valid_to = advert.get("valid_to").toString();
        this.title = advert.get("title").toString();
        this.description = advert.get("description").toString();
        this.category_id = advert.get("category_id").toString();
        this.advertiser_type = advert.get("advertiser_type").toString();
        this.external_id = advert.get("external_id").toString();
        this.external_url = advert.get("external_url").toString();
        this.contact = (Map<String, Object>) advert.get("contact");
        this.location = (Map<String, Object>) advert.get("location");
        this.images = (List<String>) advert.get("images");
        this.price = (Map<String, Object>) advert.get("price");
        this.salary = advert.get("salary").toString();
        this.attributes = (List<Map<String, Object>>) advert.get("attributes");
        this.courier = advert.get("courier").toString();
    }
}

