package ProjektInz.RESTAPI.repository;

import ProjektInz.RESTAPI.restApi.CodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface CodeEntityRepository extends JpaRepository<CodeEntity, String>  {


    @Query(value = "select code from code order by id desc limit 1", nativeQuery = true)
    String getCode();
}
