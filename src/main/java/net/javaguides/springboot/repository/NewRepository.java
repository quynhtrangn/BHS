package net.javaguides.springboot.repository;

import net.javaguides.springboot.models.Market;
import net.javaguides.springboot.models.New;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface NewRepository extends JpaRepository<New,Long> {
    Optional<New> findById(Long new_id);

    @Query(value = "SELECT * FROM New WHERE user_id = :id", nativeQuery = true)
    List<New> findMarketByUser(@Param("id") long id);
}