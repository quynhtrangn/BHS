package net.javaguides.springboot.service;

import lombok.RequiredArgsConstructor;
import net.javaguides.springboot.exception.ResourceNotFoundException;
import net.javaguides.springboot.models.City;
import net.javaguides.springboot.repository.CityRepository;
import net.javaguides.springboot.request.CityRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CityService {
    private final CityRepository repository;
    public void addCity(CityRequest city ){
        if(city!=null){
            var city1 = City.builder()
                    .city_id(city.getCity_id())
                    .city_name(city.getCity_name())
                    .build();
            repository.save(city1);
        }
    }
    public City updateCity(long id, City city){
        if(city!=null){
            City city1 = repository.getById(id);
            if(city1!=null){
                city1.setCity_name(city1.getCity_name());
                return repository.save(city1);
            }
        }
        return null;
    }

    public boolean deleteCity(long id){
        if(id>=1){
            City city1 = repository.getById(id);
            if(city1!=null){
                repository.delete(city1);
                return true;
            }
        }
        return false;
    }

    public void save(CityRequest request){
        var city = City.builder()
                .city_name(request.getCity_name())
                .build();
        repository.save(city);
    }
    public List<City> findAll() {
        return repository.findAll();
    }


    public City getOneCity(long id){
        return repository.getById(id);
    }

    public void deleteCityId(long id) {
        City city = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("City could not be delete"));
        repository.delete(city);
    }

    public CityRequest updateCity(CityRequest request, long id) {
        City city = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("City could not be updated"));

        city.setCity_name(request.getCity_name());
        City updatedCity = repository.save(city);
        return mapToDto(updatedCity);
    }
    public CityRequest getCityById(long id) {
        City city = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("City could not be found"));
        return mapToDto(city);
    }
    private CityRequest mapToDto(City city) {
        CityRequest request = new CityRequest();
        request.setCity_id(city.getCity_id());
        request.setCity_name(city.getCity_name());
        return request;
    }
}
