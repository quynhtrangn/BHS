package net.javaguides.springboot.controller;

import lombok.RequiredArgsConstructor;
import net.javaguides.springboot.request.FollowRequest;
import net.javaguides.springboot.service.FollowService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth/follows")
@RequiredArgsConstructor
public class FollowController {
    private final FollowService service;

    @PostMapping("/addFollow")
    public ResponseEntity<?> save(
            @RequestBody FollowRequest request
    ) {
        service.save(request);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/user/{user_id}/addFollow/{new_id}")
    public ResponseEntity<?> save(
            @PathVariable(value = "user_id") long user_id,
            @PathVariable(value = "new_id") long new_id,
            @RequestBody FollowRequest request
    ) {
        return new ResponseEntity<>(service.createFollow(user_id, new_id,request), HttpStatus.CREATED);
    }

    @GetMapping("/user/{user_id}/getFollow/{new_id}")
    public FollowRequest workPlaceDetail(
            @PathVariable(value = "user_id") long user_id,
            @PathVariable(value = "new_id") long new_id
    ){
        return service.getFollowByUserNew(user_id,new_id);
    }

    @GetMapping("/user/{user_id}/getFollow")
    public List<FollowRequest> getFollowDetailByUser(
            @PathVariable(value = "user_id") long user_id
    ){
        return service.getFollowByUser(user_id);
    }
    @GetMapping("/getFollow/{new_id}")
    public ResponseEntity<?> getFollowDetailByUserNew(
            @PathVariable("new_id") long new_id
    ){
        return ResponseEntity.ok(service.getFollowByNew(new_id));
    }

    @GetMapping
    public List<FollowRequest> getAllFollow(
    ){
        return service.findAll();
    }





    @PutMapping("/{user_id}/updateFollow/{new_id}")
    public ResponseEntity<?> updateNew(@PathVariable(value = "user_id") long user_id, @PathVariable(value = "new_id") long new_id,
                                          @RequestBody FollowRequest request) {
        FollowRequest updateFollow = service.updateFollow(user_id,new_id,request);
        return new ResponseEntity<>(updateFollow, HttpStatus.OK);
    }

    @DeleteMapping("/{user_id}/delete/{new_id}")
    public ResponseEntity<String> deleteFollow(@PathVariable("user_id") long user_id, @PathVariable("new_id") long new_id) {
        service.deleteFollow(user_id, new_id);
        return new ResponseEntity<>("Work Place delete", HttpStatus.OK);
    }
}
