package net.javaguides.springboot.repository;

import net.javaguides.springboot.models.WorkWith;
import net.javaguides.springboot.models.WorkWith;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface WorkWithRepository extends JpaRepository<WorkWith,Long> {
    Optional<WorkWith> findById(Long workWith_id);

    @Query(value = "SELECT * FROM work_with", nativeQuery = true)
    List<WorkWith> findAllWorkWith();
    @Query(value = "SELECT * FROM work_with WHERE user_id = :user_id", nativeQuery = true)
    List<WorkWith> findByUser(@Param("user_id") long user_id);

    @Query(value = "SELECT * FROM work_with WHERE field_id = :field_id", nativeQuery = true)
    List<WorkWith> findByField(@Param("field_id") long field_id);

    @Query(value = "SELECT * FROM work_with WHERE user_id = :user_id and field_id = :field_id", nativeQuery = true)
    WorkWith findByUserField(@Param("user_id") long user_id,@Param("field_id") long field_id);

}