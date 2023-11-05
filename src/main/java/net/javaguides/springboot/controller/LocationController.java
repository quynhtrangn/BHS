package net.javaguides.springboot.controller;

import lombok.RequiredArgsConstructor;
import net.javaguides.springboot.request.LocationRequest;
import net.javaguides.springboot.service.LocationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth/locations")
@RequiredArgsConstructor
public class LocationController {
    private final LocationService service;

    @GetMapping("/{id}")
    public ResponseEntity<?> locationDetail(@PathVariable long id) {
        return ResponseEntity.ok(service.getLocationById(id));
    }

    @PostMapping("/Location/{user_id}/user")
    public ResponseEntity<LocationRequest> createLocation(@PathVariable(value = "user_id") long user_id, @RequestBody LocationRequest locationRequest) {
        return new ResponseEntity<>(service.createLocation(user_id, locationRequest), HttpStatus.CREATED);
    }

    @GetMapping("/getAllLocations")
    public List<LocationRequest> findAllLocations() {
        return service.findAll();
    }

    @GetMapping("/getLocationByUser/{id}")
    public List<LocationRequest> findLocationByUser(
            @PathVariable("id")long user_id
    ) {

        return  service.getLocationByUser(user_id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteLocation(@PathVariable("id") long location_id) {
        service.deleteLocation(location_id);
        return new ResponseEntity<>("Location delete", HttpStatus.OK);
    }



    @PutMapping("/{location_id}/user")
    public ResponseEntity<?> updateLocation(@PathVariable(value = "location_id") long location_id,
                                          @RequestBody LocationRequest request) {
        LocationRequest updatedLocation = service.updateLocation(location_id,request);
        return new ResponseEntity<>(updatedLocation, HttpStatus.OK);
    }
}
