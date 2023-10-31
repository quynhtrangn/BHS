package net.javaguides.springboot.controller;

import lombok.RequiredArgsConstructor;
import net.javaguides.springboot.request.CityRequest;
import net.javaguides.springboot.service.CityService;
import net.javaguides.springboot.repository.MarketRepository;
import net.javaguides.springboot.models.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth/cities")
@RequiredArgsConstructor
public class CityController {
    private final CityService service;

    @GetMapping("/{id}")
    public ResponseEntity<?> cityDetail(@PathVariable long id) {
        return ResponseEntity.ok(service.getCityById(id));

    }

    @PostMapping("/addCity")
    public ResponseEntity<?> save(
            @RequestBody CityRequest request
    ){
        service.save(request);
        return ResponseEntity.accepted().build();
    }
    @GetMapping("/getAllCities")
    public ResponseEntity<List<City>> findAllCities() {
        return ResponseEntity.ok(service.findAll());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCity(@PathVariable("id") long city_id) {
        service.deleteCity(city_id);
        return new ResponseEntity<>("City delete", HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updatePokemon(
            @RequestBody CityRequest request, @PathVariable("id") long city_id
    ) {
        CityRequest response = service.updateCity(request, city_id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }



}
