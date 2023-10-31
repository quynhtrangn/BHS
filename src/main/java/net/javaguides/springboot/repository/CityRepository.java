package net.javaguides.springboot.repository;

import net.javaguides.springboot.models.City;
import net.javaguides.springboot.models.Market;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CityRepository extends JpaRepository<City,Long> {
    Optional<City> findById(Long city_id);

}
