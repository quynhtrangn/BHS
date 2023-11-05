package net.javaguides.springboot.service;


import lombok.RequiredArgsConstructor;
import net.javaguides.springboot.exception.ResourceNotFoundException;
import net.javaguides.springboot.models.New;
import net.javaguides.springboot.repository.NewRepository;
import net.javaguides.springboot.request.NewRequest;
import net.javaguides.springboot.user.User;
import net.javaguides.springboot.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NewService {
    private final NewRepository repository;
    private final UserRepository userRepository;

    public NewRequest createNew(long users_id, NewRequest request) {
        New news = mapToEntity(request);

        User users = userRepository.findById(users_id).orElseThrow(() -> new ResourceNotFoundException("User with associated review not found"));

        news.setUsers(users);

        New newNew = repository.save(news);

        return mapToRequest(newNew);
    }
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

    public List<NewRequest> findAll() {
        List<New> markets = repository.findAll();
        return markets.stream().map(market -> mapToRequest(market)).collect(Collectors.toList());
    }

    public List<NewRequest> getNewByUser(long id) {

        List<New> markets = repository.findNewByUser(id);
        return markets.stream().map(market -> mapToRequest(market)).collect(Collectors.toList());
    }

    public boolean deleteNew(long id){
        if(id>=1){
            New news = repository.getById(id);
            if(news!=null){
                repository.delete(news);
                return true;
            }
        }
        return false;
    }



    public NewRequest updateNew(long new_id, long users_id, NewRequest request) {
        New news = repository.findById(new_id).orElseThrow(() -> new ResourceNotFoundException("New with associated users not found"));

        User users = userRepository.findById(users_id).orElseThrow(() -> new ResourceNotFoundException("User with associate market not found"));

        if(news.getUsers().getId() != users.getId()) {
            throw new ResourceNotFoundException("This User does not belong to a New");
        }

        news.setNew_name(request.getNew_name());
        news.setNew_img(request.getNew_img());
        news.setNew_description(request.getNew_description());
        news.setQuantity(request.getQuantity());
        news.setCost(request.getCost());
        news.setStart_time(request.getStart_time());
        news.setEnd_time(request.getEnd_time());
        news.setStatus(request.getStatus());
        news.setUsers(mapToEntity(request).users);
        New updateNew = repository.save(news);

        return mapToRequest(updateNew);
    }


    public NewRequest getNewById(long id) {
        New market = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("New could not be found"));
        return mapToRequest(market);
    }

    private New mapToEntity(NewRequest request) {
        New news = new New();

        news.setNew_id(request.getNew_id());
        news.setNew_name(request.getNew_name());
        news.setNew_img(request.getNew_img());
        news.setNew_description(request.getNew_description());
        news.setQuantity(request.getQuantity());
        news.setCost(request.getCost());
        news.setStart_time(request.getStart_time());
        news.setEnd_time(request.getEnd_time());
        news.setStatus(request.getStatus());
        news.setUsers(request.users);
        return news;
    }
    private NewRequest mapToRequest(New news) {

        NewRequest request = new NewRequest();
        request.setNew_id(news.getNew_id());
        request.setNew_name(news.getNew_name());
        request.setNew_img(news.getNew_img());
        request.setNew_description(news.getNew_description());
        request.setQuantity(news.getQuantity());
        request.setCost(news.getCost());
        request.setStart_time(news.getStart_time());
        request.setEnd_time(news.getEnd_time());
        request.setStatus(news.getStatus());
        request.setUsers(news.users);

        return request;
    }

}
