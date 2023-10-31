package net.javaguides.springboot.service;


import lombok.RequiredArgsConstructor;
import net.javaguides.springboot.models.New;
import net.javaguides.springboot.repository.NewRepository;
import net.javaguides.springboot.request.NewRequest;
import net.javaguides.springboot.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NewService {
    private final NewRepository repository;
    private final UserRepository userRepository;
    public void save(NewRequest request){
        var news = New.builder()
                .new_name(request.getNew_name())
                .quantity(request.getQuantity())
                .cost(request.getCost())
                .start_time(request.getStart_time())
                .end_time(request.getEnd_time())
                .status(request.getStatus())
                .build();
        repository.save(news);
    }
//    public List<NewRequest> findAll() {
//        List<Market> markets = repository.findAll();
//        return markets.stream().map(market -> mapToDto(market)).collect(Collectors.toList());
//    }
//
//    public List<NewRequest> getNewByCity(long id) {
//
//        List<New> markets = repository.findNewByCity(id);
//        return markets.stream().map(market -> mapToDto(market)).collect(Collectors.toList());
//    }
//
//    public boolean deleteNew(long id){
//        if(id>=1){
//            New market1 = repository.getById(id);
//            if(market1!=null){
//                repository.delete(market1);
//                return true;
//            }
//        }
//        return false;
//    }
//
//
//
//    public NewRequest updateNew(long newsId, long cityId, NewRequest request) {
//        New news = repository.findById(newsId).orElseThrow(() -> new ResourceNotFoundException("New with associated city not found"));
//
//        City city = userRepository.findById(cityId).orElseThrow(() -> new ResourceNotFoundException("City with associate news not found"));
//
//        if(news.getCity().getCity_id() != city.getCity_id()) {
//            throw new ResourceNotFoundException("This news does not belong to a city");
//        }
//
//        news.setNew_name(request.getNew_name());
//        news.setAddress(request.getAddress());
//
//        New updateNew = repository.save(news);
//
//        return mapToDto(updateNew);
//    }
//
//
//    public NewRequest getNewById(long id) {
//        New news = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("New could not be found"));
//        return mapToDto(news);
//    }
//    private NewRequest mapToDto(New news) {
//
//        NewRequest request = new NewRequest();
//        request.setNew_id(news.getNew_id());
//        request.setNew_name(news.getNew_name());
//        request.setAddress(news.getAddress());
//        request.setCity(news.city);
//        return request;
//    }
}
