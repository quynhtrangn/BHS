package net.javaguides.springboot.service;

import lombok.RequiredArgsConstructor;
import net.javaguides.springboot.exception.ResourceNotFoundException;
import net.javaguides.springboot.models.City;
import net.javaguides.springboot.models.Field;
import net.javaguides.springboot.models.Field;
import net.javaguides.springboot.models.WorkPlace;
import net.javaguides.springboot.repository.FieldRepository;
import net.javaguides.springboot.repository.FieldRepository;
import net.javaguides.springboot.request.FieldRequest;
import net.javaguides.springboot.request.FieldRequest;
import net.javaguides.springboot.request.WorkPlaceRequest;
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

    public FieldRequest getFieldById(long id) {
        Field field = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Field could not be found"));
        return mapToRequest(field);
    }
    public FieldRequest updateField( long id, FieldRequest request) {
        Field field = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Field could not be updated"));

        field.setFieldName(request.getFieldName());
        Field updatedField = repository.save(field);
        return mapToRequest(updatedField);
    }
    public boolean deleteField(long id){
        if(id>=1){
            Field field1 = repository.getById(id);
            if(field1!=null){
                repository.delete(field1);
                return true;
            }
        }
        return false;
    }
    private FieldRequest mapToRequest(Field field) {
        FieldRequest request = new FieldRequest();
        request.setField_id(field.getField_id());
        request.setFieldName(field.getFieldName());
        return request;
    }

}
