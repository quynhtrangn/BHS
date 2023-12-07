package net.javaguides.springboot.controller;


import lombok.RequiredArgsConstructor;
import net.javaguides.springboot.request.CareRequest;
import net.javaguides.springboot.request.WorkPlaceRequest;
import net.javaguides.springboot.service.CareService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth/cares")
@RequiredArgsConstructor
@CrossOrigin

public class CareController {
    private final CareService service;

    @PostMapping("/addCare")
    public ResponseEntity<?> save(
            @RequestBody CareRequest request
    ) {
        service.save(request);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/user/{user_id}/addCare/{usercare_id}")
    public ResponseEntity<?> save(
            @PathVariable(value = "user_id") long user_id,
            @PathVariable(value = "usercare_id") long usercare_id,
            @RequestBody CareRequest request
    ) {
        return new ResponseEntity<>(service.createCare(user_id, usercare_id,request), HttpStatus.CREATED);
    }

    @GetMapping("/user/{user_id}/getCare/{usercare_id}")
    public CareRequest careDetail(
            @PathVariable(value = "user_id") long user_id,
            @PathVariable(value = "usercare_id") long usercare_id
    ){
        return service.getCareByUserUserCared(user_id,usercare_id);
    }

    @GetMapping("/user/{user_id}/getCare")
    public List<CareRequest> getCareDetailByUser(
            @PathVariable(value = "user_id") long user_id
    ){
        return service.getCareByUser(user_id);
    }
    @GetMapping("/getCare/{usercare_id}")
    public ResponseEntity<?> getCareDetailByUserField(
            @PathVariable("usercare_id") long usercare_id
    ){
        return ResponseEntity.ok(service.getCareByUserCare(usercare_id));
    }

    @GetMapping
    public List<CareRequest> getAllCare(
    ){
        return service.findAllCare();
    }





    @PutMapping("/{user_id}/updateCare/{usercared_id}")
    public ResponseEntity<?> updateMarket(@PathVariable(value = "user_id") long user_id, @PathVariable(value = "usercared_id") long usercared_id,
                                          @RequestBody CareRequest request) {
        CareRequest updateWorkPlace = service.updateCare(user_id,usercared_id,request);
        return new ResponseEntity<>(updateWorkPlace, HttpStatus.OK);
    }

    @DeleteMapping("/{user_id}/delete/{market_id}")
    public ResponseEntity<String> deleteCare(@PathVariable("user_id") long user_id, @PathVariable("market_id") long market_id) {
        service.deleteCare(user_id, market_id);
        return new ResponseEntity<>("Care delete", HttpStatus.OK);
    }
    
}
