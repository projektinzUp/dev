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
        String query = createCodeTable() + createOLXAdvertTable() + createAllegroAdvertTable();
        entityManager.createNativeQuery(query).executeUpdate();
    }

    private String createOLXAdvertTable(){
        return "CREATE TABLE IF NOT EXISTS advert(\"id\" Varchar, \"title\" Varchar, " +
                "\"description\" Varchar, \"url\" Varchar, \"images\" Varchar, \"price\" Int, PRIMARY KEY(\"id\"));\n";
    }

    private String createCodeTable(){
        return "CREATE TABLE IF NOT EXISTS code(\"timestamp\" TimeStamp default current_timestamp, \"code\" Varchar, " +
                "PRIMARY KEY(\"code\"));\n";
    }

    private String createAllegroAdvertTable(){
        return "CREATE TABLE IF NOT EXISTS advertallegro(\"id\" Varchar, \"title\" Varchar, " +
                "\"url\" Varchar, \"images\" Varchar, \"price\" Real, PRIMARY KEY(\"id\"));\n";
    }

}
