package net.javaguides.springboot.repository;

import net.javaguides.springboot.models.Follow;
import net.javaguides.springboot.models.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow,Long> {
    Optional<Follow> findById(Long follow_id);

    @Query(value = "SELECT * FROM follow", nativeQuery = true)
    List<Follow> findAllFollow();
    @Query(value = "SELECT * FROM follow WHERE users_id = :users_id", nativeQuery = true)
    List<Follow> findByUser(@Param("users_id") long users_id);

    @Query(value = "SELECT * FROM follow WHERE new_id = :new_id", nativeQuery = true)
    List<Follow> findByNew(@Param("new_id") long new_id);

    @Query(value = "SELECT * FROM follow WHERE users_id = :users_id and new_id = :new_id", nativeQuery = true)
    Follow findByUserNew(@Param("users_id") long users_id,@Param("new_id") long new_id);

}