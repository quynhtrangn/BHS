package net.javaguides.springboot.repository;

import jakarta.transaction.Transactional;
import net.javaguides.springboot.models.Location;
import net.javaguides.springboot.models.Market;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface LocationRepository extends JpaRepository<Location,Long> {
    Optional<Location> findById(Long location_id);

    @Query(value = "SELECT * FROM location WHERE user_id = :id", nativeQuery = true)
    List<Location> findLocationByUser(@Param("id") long id);

//    @Transactional
//    @Modifying
//    @Query(value = "UPDATE location SET end_time = :end_time, start_time = :start_time, status = :status,user_id: user_id WHERE location_id = :location_id",nativeQuery = true)
//    void updateLocation(@Param("location_id") long location_id,
//                        @Param("start_time") LocalDateTime start_time,
//                        @Param("end_time") LocalDateTime end_time,
//                        @Param("status") int status,
//                        @Param("user_id") long user_id);

}
