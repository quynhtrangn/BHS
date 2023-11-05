package net.javaguides.springboot.service;



import lombok.RequiredArgsConstructor;
import net.javaguides.springboot.exception.ResourceNotFoundException;

import net.javaguides.springboot.models.City;
import net.javaguides.springboot.models.Location;
import net.javaguides.springboot.models.Location;
import net.javaguides.springboot.models.Market;
import net.javaguides.springboot.repository.LocationRepository;
import net.javaguides.springboot.request.LocationRequest;
import net.javaguides.springboot.request.LocationRequest;
import net.javaguides.springboot.user.User;
import net.javaguides.springboot.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LocationService {
    private final LocationRepository repository;
    private final UserRepository userRepository;
    public void save(LocationRequest request){
        var location = Location.builder()
                .start_time(request.getStart_time())
                .end_time(request.getEnd_time())
                .status(request.getStatus())
                .user(request.getUser())
                .build();
        repository.save(location);
    }
    public LocationRequest createLocation(long user_id, LocationRequest request) {
        Location location = mapToEntity(request);
        User user_s = userRepository.findById(user_id).orElseThrow(() -> new ResourceNotFoundException("User with associated review not found"));
        location.setUser(user_s);
        Location newLocation = repository.save(location);
        return mapToRequest(newLocation);
    }
    public List<LocationRequest> findAll() {
        List<Location> locations = repository.findAll();
        return locations.stream().map(location -> mapToRequest(location)).collect(Collectors.toList());
    }

    public List<LocationRequest> getLocationByUser(long id) {

        List<Location> location = repository.findLocationByUser(id);
        return location.stream().map(location1 -> mapToRequest(location1)).collect(Collectors.toList());
    }

    public boolean deleteLocation(long id){
        if(id>=1){
            Location location = repository.getById(id);
            if(location!=null){
                repository.delete(location);
                return true;
            }
        }
        return false;
    }



    public LocationRequest updateLocation(long location_id, LocationRequest request) {
        Location location = repository.findById(location_id).orElseThrow(() -> new ResourceNotFoundException("Location with associated user not found"));
//        User user = userRepository.findById(user_id).orElseThrow(() -> new ResourceNotFoundException("User with associate market not found"));
//
//        if(location.user.getId() != user.getId()) {
//            throw new ResourceNotFoundException("This user does not belong to a location");
//        }
        location.setStart_time(request.getStart_time());
        location.setEnd_time(request.getEnd_time());
        location.setStatus(request.getStatus());
        location.setUser(mapToEntity(request).user);
//        Location updateLocation = repository.save(location);
//        repository.updateLocation(location_id,
//                request.getStart_time(),
//                request.getEnd_time(),
//                request.getStatus(),
//                request.user.getId());
        Location updateLocation = repository.save(location);
        return mapToRequest(updateLocation);
    }


    public LocationRequest getLocationById(long id) {
        Location location = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Location could not be found"));
        return mapToRequest(location);
    }
    private Location mapToEntity(LocationRequest request) {
        Location location = new Location();
        location.setLocation_id(request.getLocation_id());
        location.setStart_time(request.getStart_time());
        location.setEnd_time(request.getEnd_time());
        location.setStatus(request.getStatus());
        location.setUser(request.getUser());

        return location;
    }
    private LocationRequest mapToRequest(Location location) {
        LocationRequest request = new LocationRequest();
        
        request.setLocation_id(location.getLocation_id());
        request.setStart_time(location.getStart_time());
        request.setEnd_time(location.getEnd_time());
        request.setStatus(location.getStatus());
        request.setUser(location.user);
        return request;
    }
}
