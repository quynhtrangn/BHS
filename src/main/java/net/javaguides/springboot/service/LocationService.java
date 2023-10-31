package net.javaguides.springboot.service;



import lombok.RequiredArgsConstructor;
import net.javaguides.springboot.models.Location;
import net.javaguides.springboot.repository.LocationRepository;
import net.javaguides.springboot.request.LocationRequest;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class LocationService {
    private final LocationRepository repository;
    public void save(LocationRequest request){
        var location = Location.builder()
                .start_time(request.getStart_time())
                .end_time(request.getEnd_time())
                .status(request.getStatus())
                .user(request.getUser())
                .build();
        repository.save(location);
    }
    public List<Location> findAll() {
        return repository.findAll();
    }
}
