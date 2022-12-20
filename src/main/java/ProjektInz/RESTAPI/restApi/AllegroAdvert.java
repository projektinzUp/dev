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
import java.util.Map;

@Getter
@Setter
@Entity
@Table(name = "advertallegro")
@NoArgsConstructor
@AllArgsConstructor
public class AllegroAdvert implements Persistable<String> {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "url")
    private String url;
    @Column(name = "title")
    private String name;
    @Column(name = "images")
    private String images;
    @Column(name="price")
    private Double price;

    public AllegroAdvert(Map<String, Object> advert) {
        this.id = advert.get("id").toString();
        this.name = advert.get("name").toString();
        this.url = "https://allegro.pl.allegrosandbox.pl/oferta/" + this.name.replace(" ", "-")+ "-" + this.id;
        this.images = String.valueOf(advert.get("primaryImage")).replace("{url=", "").replace("}", "");
        LinkedHashMap<String, Object> linkedHashMapSellingMode = (LinkedHashMap<String, Object>) advert.get("sellingMode");
        LinkedHashMap<String, Object> linkedHashMapPrice = (LinkedHashMap<String, Object>) linkedHashMapSellingMode.get("price");
        this.price = Double.parseDouble(String.valueOf(linkedHashMapPrice.get("amount")));
    }
    @Override
    public boolean isNew() {
        return false;
    }
}
