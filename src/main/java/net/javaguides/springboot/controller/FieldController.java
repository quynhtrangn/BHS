package net.javaguides.springboot.controller;

import lombok.RequiredArgsConstructor;
import net.javaguides.springboot.exception.ResourceNotFoundException;
import net.javaguides.springboot.models.City;
import net.javaguides.springboot.models.Field;
import net.javaguides.springboot.models.Market;
import net.javaguides.springboot.repository.FieldRepository;
import net.javaguides.springboot.request.FieldRequest;
import net.javaguides.springboot.service.FieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth/fields")
@RequiredArgsConstructor
public class FieldController {
    private final FieldService service;
    @Autowired
    private FieldRepository fieldRepository;


    @PostMapping
    public ResponseEntity<?> save(
            @RequestBody FieldRequest request
    ) {
        service.save(request);
        return ResponseEntity.accepted().build();
    }

    @GetMapping
    public ResponseEntity<List<Field>> findAllFields() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<Field> getMarketByCity(@PathVariable long id){
        Field field = fieldRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Field not exits with id:" + id));
        return ResponseEntity.ok(field);
    }
}
