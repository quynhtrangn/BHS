package net.javaguides.springboot.repository;

import net.javaguides.springboot.models.Field;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FieldRepository extends JpaRepository<Field,Long> {
    @Override
    Optional<Field> findById(Long field_id);
}
