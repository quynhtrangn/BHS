package net.javaguides.springboot.controller;

import lombok.RequiredArgsConstructor;
import net.javaguides.springboot.request.NewRequest;
import net.javaguides.springboot.service.NewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth/news")
@RequiredArgsConstructor
public class NewController {
    private final NewService service;

    @GetMapping("/{id}")
    public ResponseEntity<?> newsDetail(@PathVariable long id) {
        return ResponseEntity.ok(service.getNewByUser(id));
    }

    @PostMapping("/New/{user_id}/user")
    public ResponseEntity<NewRequest> createReview(@PathVariable(value = "user_id") long user_id, @RequestBody NewRequest newRequest) {
        return new ResponseEntity<>(service.createNew(user_id, newRequest), HttpStatus.CREATED);
    }

    @GetMapping("/getAllNews")
    public List<NewRequest> findAllNews() {
        return service.findAll();
    }

    @GetMapping("/getNewByUser/{id}")
    public List<NewRequest> findNewByUser(
            @PathVariable("id")long user_id
    ) {

        return  service.getNewByUser(user_id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteNew(@PathVariable("id") long new_id) {
        service.deleteNew(new_id);
        return new ResponseEntity<>("New delete", HttpStatus.OK);
    }



    @PutMapping("/{new_id}/user/{user_id}")
    public ResponseEntity<?> updateNew(@PathVariable(value = "new_id") long new_id, @PathVariable(value = "user_id") long user_id,
                                          @RequestBody NewRequest request) {
        NewRequest updatedNew = service.updateNew(new_id,user_id,request);
        return new ResponseEntity<>(updatedNew, HttpStatus.OK);
    }
}
