package net.javaguides.springboot.controller;

import lombok.RequiredArgsConstructor;
import net.javaguides.springboot.models.WorkPlace;
import net.javaguides.springboot.request.MarketRequest;
import net.javaguides.springboot.request.WorkPlaceRequest;
import net.javaguides.springboot.service.MarketService;
import net.javaguides.springboot.service.WorkPlaceService;
import net.javaguides.springboot.user.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/auth/workPlaces")
@RequiredArgsConstructor
public class WorkPlaceController {
    private final WorkPlaceService service;

//    @PutMapping("/{user_Id}/market/{market_id}")
//    public User addWorkPlace(
//            @PathVariable Long user_Id,
//            @PathVariable Long market_id
//    ){
//        return service.addWorkPlace(user_Id, market_id);
//    }


    @PostMapping("/addWorkPlace")
    public ResponseEntity<?> save(
            @RequestBody WorkPlaceRequest request
    ) {
        service.save(request);
        return ResponseEntity.accepted().build();
    }

//    @PostMapping("/{market_id}/market/{user_id}/user")
//    public ResponseEntity<WorkPlaceRequest> createReview(@PathVariable(value = "market_id") long market_id,@PathVariable(value = "user_id") long user_id, @RequestBody WorkPlaceRequest workPlaceRequest) {
//        return new ResponseEntity<>(service.createWorkPlace(market_id,user_id, workPlaceRequest), HttpStatus.CREATED);
//    }
    @PostMapping("/user/{user_id}/addWorkPlace/{market_id}")
    public ResponseEntity<?> save(
            @PathVariable(value = "user_id") long user_id,
            @PathVariable(value = "market_id") long market_id,
            @RequestBody WorkPlaceRequest request
    ) {
        return new ResponseEntity<>(service.createWorkPlace(user_id, market_id,request), HttpStatus.CREATED);
    }

    @GetMapping("/user/{user_id}/getWorkPlace/{market_id}")
    public WorkPlaceRequest workPlaceDetail(
            @PathVariable(value = "user_id") long user_id,
            @PathVariable(value = "market_id") long market_id
    ){
        return service.getWorkPlaceByUserMarket(user_id,market_id);
    }

    @GetMapping("/user/{user_id}/getWorkPlace")
    public List<WorkPlaceRequest> getWorkPlaceDetailByUser(
            @PathVariable(value = "user_id") long user_id
            ){
        return service.getWorkPlaceByUser(user_id);
    }
    @GetMapping("/getWorkPlace/{market_id}")
    public ResponseEntity<?> getWorkPlaceDetailByUserMarket(
            @PathVariable("market_id") long market_id
    ){
        return ResponseEntity.ok(service.getWorkPlaceByMarket(market_id));
    }

    @GetMapping
    public List<WorkPlaceRequest> getAllWorkPlace(
    ){
        return service.findAllWorkPlace();
    }





    @PutMapping("/{user_id}/updateWorkPlace/{market_id}")
    public ResponseEntity<?> updateMarket(@PathVariable(value = "user_id") long user_id, @PathVariable(value = "market_id") long market_id,
                                          @RequestBody WorkPlaceRequest request) {
        WorkPlaceRequest updateWorkPlace = service.updateWorkPlace(user_id,market_id,request);
        return new ResponseEntity<>(updateWorkPlace, HttpStatus.OK);
    }

    @DeleteMapping("/{user_id}/delete/{market_id}")
    public ResponseEntity<String> deleteWorkPlace(@PathVariable("user_id") long user_id, @PathVariable("market_id") long market_id) {
        service.deleteWorkPlace(user_id, market_id);
        return new ResponseEntity<>("Work Place delete", HttpStatus.OK);
    }


}
