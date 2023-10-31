package net.javaguides.springboot.repository;

import net.javaguides.springboot.models.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow,Long> {
    Optional<Follow> findById(Long follow_id);
}