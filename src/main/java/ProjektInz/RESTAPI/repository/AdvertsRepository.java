package ProjektInz.RESTAPI.repository;

import ProjektInz.RESTAPI.restApi.Advert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface AdvertsRepository extends JpaRepository<Advert, String> {

    @Query(value = "select * from advert adv where adv.title like %:keyword%", nativeQuery = true)
    List<Advert> findByKeyword(@Param("keyword") String keyword);

//    String olxGetCode(String keyword);
}
