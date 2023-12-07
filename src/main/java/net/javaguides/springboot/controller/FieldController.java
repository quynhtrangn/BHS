package net.javaguides.springboot.controller;

import lombok.RequiredArgsConstructor;
import net.javaguides.springboot.exception.ResourceNotFoundException;
import net.javaguides.springboot.models.City;
import net.javaguides.springboot.models.Field;
import net.javaguides.springboot.models.Market;
import net.javaguides.springboot.repository.FieldRepository;
import net.javaguides.springboot.request.FieldRequest;
import net.javaguides.springboot.request.MarketRequest;
import net.javaguides.springboot.service.FieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth/fields")
@RequiredArgsConstructor
@CrossOrigin

public class FieldController {
    private final FieldService service;
    @Autowired
    private FieldRepository repository;


    @PostMapping
    public ResponseEntity<?> save(
            @RequestBody FieldRequest request
    ) {
        service.save(request);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/getAllFields")
    public ResponseEntity<List<Field>> findAllFields() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getFieldById(@PathVariable long id){
        return ResponseEntity.ok(service.getFieldById(id));
    }

    @PutMapping("/field/{field_id}")
    public ResponseEntity<?> updateMarket( @PathVariable(value = "field_id") long field_id,
                                          @RequestBody FieldRequest request) {
        FieldRequest updatedField = service.updateField(field_id,request);
        return new ResponseEntity<>(updatedField, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCity(@PathVariable("id") long field_id) {
        service.deleteField(field_id);
        return new ResponseEntity<>("Field delete", HttpStatus.OK);
    }


}
