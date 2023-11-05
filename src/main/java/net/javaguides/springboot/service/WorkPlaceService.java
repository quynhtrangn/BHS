package net.javaguides.springboot.service;

import lombok.RequiredArgsConstructor;
import net.javaguides.springboot.exception.ResourceNotFoundException;
import net.javaguides.springboot.models.City;
import net.javaguides.springboot.models.Market;
import net.javaguides.springboot.models.WorkPlace;
import net.javaguides.springboot.repository.MarketRepository;
import net.javaguides.springboot.repository.WorkPlaceRepository;
import net.javaguides.springboot.request.MarketRequest;
import net.javaguides.springboot.request.WorkPlaceRequest;
import net.javaguides.springboot.request.WorkWithRequest;
import net.javaguides.springboot.user.User;
import net.javaguides.springboot.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WorkPlaceService{
    private final WorkPlaceRepository repository;
    private final MarketRepository marketRepository;
    private final UserRepository userRepository;

//    public User addWorkPlace(long user_id, long market_id) {
//        Set<Market> marketSet = null;
//        User users = userRepository.findById(user_id).get();
//        Market market = marketRepository.findById(market_id).get();
//        marketSet =  users.getWorkPlaces();
//        marketSet.add(market);
//        users.setWorkPlaces(marketSet);
//        return userRepository.save(users);
//    }

    public void save(WorkPlaceRequest request){
        var workPlace = WorkPlace.builder()
                .users(request.getUsers())
                .market(request.getMarket())
                .build();
        repository.save(workPlace);
    }

    public WorkPlaceRequest createWorkPlace(long user_id,long market_id, WorkPlaceRequest request) {
        WorkPlace workPlace = mapToEntity(request);

        Market market = marketRepository.findById(market_id).orElseThrow(() -> new ResourceNotFoundException("Market with associated review not found"));
        User users = userRepository.findById(user_id).orElseThrow(() -> new ResourceNotFoundException("User with associated review not found"));
        workPlace.setMarket(market);
        workPlace.setUsers(users);

        WorkPlace newWorkPlace = repository.save(workPlace);

        return mapToRequest(newWorkPlace);
    }
//    public WorkPlaceRequest createWorkPlace(long user_id,long market_id, WorkPlaceRequest request) {
//        WorkPlace workPlace = mapToEntity(request);
//
//        User users = userRepository.findById(user_id).orElseThrow(() -> new ResourceNotFoundException("User with associated review not found"));
//        Market market = marketRepository.findById(market_id).orElseThrow(() -> new ResourceNotFoundException("Market with associated review not found"));
//
//        workPlace.setUsers(users);
//        workPlace.setMarket(market);
//
//        WorkPlace newWorkPlace = repository.save(workPlace);
//
//        return mapToRequest(newWorkPlace);
//    }
    public List<WorkPlaceRequest> findAll() {
        List<WorkPlace> workPlaces = repository.findAll();
        return workPlaces.stream().map(workPlace -> mapToRequest(workPlace)).collect(Collectors.toList());
    }
    public List<WorkPlaceRequest> findAllWorkPlace() {
        List<WorkPlace> workPlaces = repository.findAllWorkPlace();
        return workPlaces.stream().map(workPlace -> mapToRequest(workPlace)).collect(Collectors.toList());
    }
    public List<WorkPlaceRequest> getWorkPlaceByUser(long id) {
        List<WorkPlace> workPlaces = repository.findByUser(id);
        return workPlaces.stream().map(workPlace -> mapToRequest(workPlace)).collect(Collectors.toList());
    }

    public List<WorkPlaceRequest> getWorkPlaceByMarket(long id) {

        List<WorkPlace> workPlaces = repository.findByMarket(id);
        return workPlaces.stream().map(workPlace -> mapToRequest(workPlace)).collect(Collectors.toList());
    }
    public WorkPlaceRequest getWorkPlaceByUserMarket(long user_id, long market_id) {
        WorkPlace workPlaces = repository.findByUserMarket(user_id,market_id);
        return mapToRequest(repository.findByUserMarket(user_id,market_id));
    }

    public WorkPlaceRequest updateWorkPlace(long user_id, long market_id, WorkPlaceRequest request) {
        WorkPlace workPlace = repository.findByUserMarket(user_id,market_id);
        User users = userRepository.findById(user_id).orElseThrow(() -> new ResourceNotFoundException("User with associate WorkPlace not found"));
        Market market = marketRepository.findById(market_id).orElseThrow(() -> new ResourceNotFoundException("Market with associated WorkPlace not found"));

       if(workPlace.getUsers().getId()!= users.getId()) {
            throw new ResourceNotFoundException("This User does not belong to a WorkPlace");
        }
       if(workPlace.getMarket().getMarket_id()!= market.getMarket_id()) {
            throw new ResourceNotFoundException("This Market does not belong to a WorkPlace");
        }
       User newUsers = userRepository.findById(request.getUsers().getId()).orElseThrow(() -> new ResourceNotFoundException("User with associate WorkPlace not found"));
       Market newMarket = marketRepository.findById(request.getMarket().getMarket_id()).orElseThrow(() -> new ResourceNotFoundException("Market with associated WorkPlace not found"));
       workPlace.setUsers(newUsers);
       workPlace.setMarket(newMarket);
       WorkPlace updateWorkPlace = repository.save(workPlace);

       return mapToRequest(updateWorkPlace);
    }

    public void deleteWorkPlace(long user_id, long market_id) {
        User users = userRepository.findById(user_id).orElseThrow(() -> new ResourceNotFoundException("User with associate WorkPlace not found"));
        Market market = marketRepository.findById(market_id).orElseThrow(() -> new ResourceNotFoundException("Market with associated WorkPlace not found"));
        WorkPlace workPlace = repository.findByUserMarket(user_id,market_id);
        if(workPlace.getUsers().getId()!= users.getId()) {
            throw new ResourceNotFoundException("This User does not belong to a WorkPlace");
        }
        if(workPlace.getMarket().getMarket_id()!= market.getMarket_id()) {
            throw new ResourceNotFoundException("This Market does not belong to a WorkPlace");
        }
        repository.delete(workPlace);
    }

    private WorkPlaceRequest mapToRequest(WorkPlace workPlace) {
        WorkPlaceRequest workPlaceRequest = new WorkPlaceRequest();
        workPlaceRequest.setWorkplace_id(workPlace.getWorkplace_id());
        workPlaceRequest.setUsers(workPlace.users);
        workPlaceRequest.setMarket(workPlace.market);
        return workPlaceRequest;
    }

    private WorkPlace mapToEntity(WorkPlaceRequest request) {
        WorkPlace workPlace = new WorkPlace();
        workPlace.setWorkplace_id(request.getWorkplace_id());
        request.setUsers(workPlace.users);
        request.setMarket(workPlace.market);
        return workPlace;
    }


}