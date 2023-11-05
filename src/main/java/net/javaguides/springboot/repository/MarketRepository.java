package net.javaguides.springboot.repository;

import jakarta.transaction.Transactional;
import net.javaguides.springboot.models.City;
import net.javaguides.springboot.models.Market;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MarketRepository extends JpaRepository<Market,Long> {
//    @Query(value = """
//      select c from Market m inner join City c\s
//      on m.city.city_id = c.city_id\s
//      where c.city_id = :city.city_id)\s
//      """)

    Optional<Market> findById(Long market_id);
    @Query(value = "SELECT * FROM Market WHERE city_id = :id", nativeQuery = true)
    List<Market> findMarketByCity(@Param("id") long id);

//    @Transactional
//    @Modifying
//    @Query(value = "UPDATE tbl_contracts SET manager_id = :managerId, technician_id = :technicianId, status = 1 WHERE id = :id",nativeQuery = true)
//    void setHandleContract(@Param("id") Integer id, @Param("managerId") Integer managerId, @Param("technicianId") Integer technicianId);

}

