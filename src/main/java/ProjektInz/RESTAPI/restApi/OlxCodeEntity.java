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
import java.sql.Timestamp;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "olxcode")
@NoArgsConstructor
@AllArgsConstructor
public class OlxCodeEntity implements Persistable<String> {
    @Column(name = "timestamp")
    private Timestamp timestamp;
    @Id
    @Column(name = "code")
    private String code;

    public OlxCodeEntity(String code) {
        this.timestamp = Timestamp.from(Instant.now());
        this.code = code;
    }

    @Override
    public String getId() {
        return null;
    }

    @Override
    public boolean isNew() {
        return false;
    }
}
