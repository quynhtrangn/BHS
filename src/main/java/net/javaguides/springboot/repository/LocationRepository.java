package net.javaguides.springboot.repository;

import net.javaguides.springboot.models.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LocationRepository extends JpaRepository<Location,Long> {
    Optional<Location> findById(Long location_id);
}