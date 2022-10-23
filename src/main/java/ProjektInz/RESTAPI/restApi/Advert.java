package ProjektInz.RESTAPI.restApi;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Persistable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Entity
@Table(name="advert")
@NoArgsConstructor
@AllArgsConstructor
public class Advert implements Persistable<String> {
    @Id
    @Column(name="id")
    private String id;
    @Column(name="url")
    private String url;
    @Column(name="title")
    private String title;
    @Column(name="description")
    private String description;
    @Column(name="images")
    private String images;
    @Column(name="price")
    private int price;


    public Advert(Map<String ,Object> advert)
    {
        this.id = advert.get("id").toString();
        this.url = advert.get("url").toString();
        this.title = advert.get("title").toString();
        this.description = advert.get("description").toString();
        List<LinkedHashMap<String, String>> imagesList = (List<LinkedHashMap<String, String>>) advert.get("images");
        this.images = imagesList.get(0).get("url");
        LinkedHashMap<String, Object> priceHashMap = (LinkedHashMap<String, Object>) advert.get("price");
        this.price = Integer.parseInt(String.valueOf(priceHashMap.get("value")));
    }

    @Override
    public boolean isNew() {
        return false;
    }
}

