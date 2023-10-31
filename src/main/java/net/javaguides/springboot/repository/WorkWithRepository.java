package net.javaguides.springboot.repository;

import net.javaguides.springboot.models.WorkWith;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WorkWithRepository extends JpaRepository<WorkWith,Long> {
    Optional<WorkWith> findById(Long workWith_id);
}