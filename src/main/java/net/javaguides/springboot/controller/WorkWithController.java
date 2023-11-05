package net.javaguides.springboot.controller;


import lombok.RequiredArgsConstructor;
import net.javaguides.springboot.request.WorkWithRequest;
import net.javaguides.springboot.service.WorkWithService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth/workWiths")
@RequiredArgsConstructor
public class WorkWithController{
    private final WorkWithService service;

    @PostMapping("/addWorkWith")
    public ResponseEntity<?> save(
            @RequestBody WorkWithRequest request
    ) {
        service.save(request);
        return ResponseEntity.accepted().build();
    }
    
    @PostMapping("/user/{user_id}/addWorkWith/{field_id}")
    public ResponseEntity<?> save(
            @PathVariable(value = "user_id") long user_id,
            @PathVariable(value = "field_id") long field_id,
            @RequestBody WorkWithRequest request
    ) {
        return new ResponseEntity<>(service.createWorkWith(user_id, field_id,request), HttpStatus.CREATED);
    }

    @GetMapping("/user/{user_id}/getWorkWith/{field_id}")
    public WorkWithRequest workWithDetail(
            @PathVariable(value = "user_id") long user_id,
            @PathVariable(value = "field_id") long field_id
    ){
        return service.getWorkWithByUserField(user_id,field_id);
    }

    @GetMapping("/user/{user_id}/getWorkWith")
    public List<WorkWithRequest> getWorkWithDetailByUser(
            @PathVariable(value = "user_id") long user_id
    ){
        return service.getWorkWithByUser(user_id);
    }
    @GetMapping("/getWorkWith/{field_id}")
    public ResponseEntity<?> getWorkWithDetailByUserField(
            @PathVariable("field_id") long field_id
    ){
        return ResponseEntity.ok(service.getWorkWithByField(field_id));
    }

    @GetMapping
    public List<WorkWithRequest> getAllWorkWith(
    ){
        return service.findAllWorkWith();
    }





    @PutMapping("/{user_id}/updateWorkWith/{field_id}")
    public ResponseEntity<?> updateField(@PathVariable(value = "user_id") long user_id, @PathVariable(value = "field_id") long field_id,
                                          @RequestBody WorkWithRequest request) {
        WorkWithRequest updateWorkWith = service.updateWorkWith(user_id,field_id,request);
        return new ResponseEntity<>(updateWorkWith, HttpStatus.OK);
    }

    @DeleteMapping("/{user_id}/delete/{field_id}")
    public ResponseEntity<String> deleteField(@PathVariable("user_id") long user_id, @PathVariable("field_id") long field_id) {
        service.deleteWorkWith(user_id, field_id);
        return new ResponseEntity<>("Work With delete", HttpStatus.OK);
    }
    
}
