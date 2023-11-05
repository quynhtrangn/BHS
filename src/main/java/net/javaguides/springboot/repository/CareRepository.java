package net.javaguides.springboot.repository;

import net.javaguides.springboot.models.Care;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CareRepository extends JpaRepository<Care,Long> {
    Optional<Care> findById(Long care_id);

    @Query(value = "SELECT * FROM care", nativeQuery = true)
    List<Care> findAllCare();
    @Query(value = "SELECT * FROM care WHERE user_id = :user_id", nativeQuery = true)
    List<Care> findByUser(@Param("user_id") long user_id);

    @Query(value = "SELECT * FROM care WHERE user_cared_id = :user_cared_id", nativeQuery = true)
    List<Care> findByUserCare(@Param("user_cared_id") long user_cared_id);

    @Query(value = "SELECT * FROM care WHERE user_id = :user_id and user_cared_id = :userCare_id", nativeQuery = true)
    Care findByUserUserCared(@Param("user_id") long user_id,@Param("userCare_id") long userCare_id);

}

