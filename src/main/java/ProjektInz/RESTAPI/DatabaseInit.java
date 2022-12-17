package ProjektInz.RESTAPI;

import ProjektInz.RESTAPI.Service.AllegroTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Transient;
import javax.transaction.Transactional;

@Service
public class DatabaseInit {

    @Autowired
    private EntityManager entityManager;

    @Transactional
    @Modifying
    public void initializeDatabase() {
        String query = createAdvertTable();
        entityManager.createNativeQuery(query).executeUpdate();
    }

    private String createAdvertTable(){
        return "CREATE TABLE IF NOT EXISTS advert(\"id\" Varchar, \"title\" Varchar, " +
                "\"description\" Varchar, \"url\" Varchar, \"images\" Varchar, \"price\" Int, PRIMARY KEY(\"id\"))";
    }


}
