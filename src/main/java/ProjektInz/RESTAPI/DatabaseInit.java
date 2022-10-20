package ProjektInz.RESTAPI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
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
        return "CREATE TABLE IF NOT EXISTS ADVERTOLX2 (\"id\" Varchar, \"title\" Varchar, " +
                "\"description\" Varchar, \"url\" Varchar, \"images\" Varchar, \"price\" Varchar, PRIMARY KEY(\"id\"))";
    }

}
