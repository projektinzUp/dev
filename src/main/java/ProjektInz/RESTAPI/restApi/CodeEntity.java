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
@Table(name = "code")
@NoArgsConstructor
@AllArgsConstructor
public class CodeEntity implements Persistable<String> {


    @Column(name = "timestamp")
    private Timestamp timestamp;
    @Id
    @Column(name = "code")
    private String code;

    public CodeEntity(String code) {
        this.code = code;
        this.timestamp = Timestamp.from(Instant.now());
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
