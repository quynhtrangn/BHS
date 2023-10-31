package net.javaguides.springboot.repository;


import net.javaguides.springboot.models.WorkPlace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface WorkPlaceRepository extends JpaRepository<WorkPlace,Long> {
    Optional<WorkPlace> findById(Long workPace_id);

    @Query(value = "SELECT * FROM work_place", nativeQuery = true)
    List<WorkPlace> findAllWorkPlace();
    @Query(value = "SELECT * FROM work_place WHERE user_id = :user_id", nativeQuery = true)
    List<WorkPlace> findByUser(@Param("user_id") long user_id);

    @Query(value = "SELECT * FROM work_place WHERE market_id = :market_id", nativeQuery = true)
    List<WorkPlace> findByMarket(@Param("market_id") long market_id);

    @Query(value = "SELECT * FROM work_place WHERE user_id = :user_id and market_id = :market_id", nativeQuery = true)
    WorkPlace findByUserMarket(@Param("user_id") long user_id,@Param("market_id") long market_id);

}