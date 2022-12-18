package ProjektInz.RESTAPI.repository;

import ProjektInz.RESTAPI.restApi.CodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;


@Repository
public interface CodeEntityRepository extends JpaRepository<CodeEntity, String>  {

    @Query(value = "select code from code order by timestamp desc limit 1", nativeQuery = true)
    String getCode();

    @Query(value = "select timestamp from code order by timestamp desc limit 1", nativeQuery = true)
    Timestamp getCodeObject();

    @Modifying
    @Transactional
    @Query(value = "delete from code where timestamp < ?1",nativeQuery = true)
    void deleteOlderThenTimestamp(Timestamp timestamp);

    @Query(value = "select * from code",nativeQuery = true)
    List<CodeEntity> getCodes();
}
