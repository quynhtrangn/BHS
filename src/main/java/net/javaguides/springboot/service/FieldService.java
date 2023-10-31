package net.javaguides.springboot.service;

import lombok.RequiredArgsConstructor;
import net.javaguides.springboot.models.Field;
import net.javaguides.springboot.repository.FieldRepository;
import net.javaguides.springboot.request.FieldRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FieldService {
    private final FieldRepository repository;
    public void save(FieldRequest request){
        var field = Field.builder()
                .fieldName(request.getFieldName())
                .build();
        repository.save(field);


    }
    public List<Field> findAll() {
        return repository.findAll();
    }
}
