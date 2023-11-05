package net.javaguides.springboot.service;

import lombok.*;
import net.javaguides.springboot.exception.ResourceNotFoundException;
import net.javaguides.springboot.models.*;
import net.javaguides.springboot.models.Care;
import net.javaguides.springboot.repository.CareRepository;
import net.javaguides.springboot.repository.FieldRepository;
import net.javaguides.springboot.repository.CareRepository;
import net.javaguides.springboot.request.CareRequest;
import net.javaguides.springboot.request.CareRequest;
import net.javaguides.springboot.user.User;
import net.javaguides.springboot.user.UserRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CareService {
    private final CareRepository repository;
    private final UserRepository userRepository;
    private final UserRepository userCaredRepository;

    public void save(CareRequest request){
        var care = Care.builder()
                .user_main(request.getUsers())
                .user_cared(request.getUserCared())
                .build();
        repository.save(care);
        
    }
    public CareRequest createCare(long user_id, long usercare_id, CareRequest request) {
        Care care = mapToEntity(request);

        User usercare = userCaredRepository.findById(usercare_id).orElseThrow(() -> new ResourceNotFoundException("User Cared with associated review not found"));
        User users = userRepository.findById(user_id).orElseThrow(() -> new ResourceNotFoundException("User with associated review not found"));
        care.setUser_main(users);
        care.setUser_cared(usercare);

        Care newCare = repository.save(care);

        return mapToRequest(newCare);
    }
    public List<CareRequest> findAllCare() {
        List<Care> workWiths = repository.findAllCare();
        return workWiths.stream().map(workWith -> mapToRequest(workWith)).collect(Collectors.toList());
    }

    public List<CareRequest> getCareByUser(long id) {
        List<Care> workWiths = repository.findByUser(id);
        return workWiths.stream().map(workWith -> mapToRequest(workWith)).collect(Collectors.toList());
    }
    public List<Care> findAll() {
        return repository.findAll();
    }

    public List<CareRequest> getCareByUserCare(long id) {

        List<Care> cares = repository.findByUserCare(id);
        return cares.stream().map(care -> mapToRequest(care)).collect(Collectors.toList());
    }
    public CareRequest getCareByUserUserCared(long user_id, long usercare_id) {
        Care care = repository.findByUserUserCared(user_id,usercare_id);
        return mapToRequest(repository.findByUserUserCared(user_id,usercare_id));
    }

    public CareRequest updateCare(long user_id, long usercare_id, CareRequest request) {
        Care care = repository.findByUserUserCared(user_id,usercare_id);
        User users = userRepository.findById(user_id).orElseThrow(() -> new ResourceNotFoundException("User with associate Care not found"));
        User usercare = userCaredRepository.findById(usercare_id).orElseThrow(() -> new ResourceNotFoundException("User Cared with associated Care not found"));

        if(care.getUser_main().getId()!= users.getId()) {
            throw new ResourceNotFoundException("This User does not belong to a Care");
        }
        if(care.getUser_cared().getId()!= usercare.getId()) {
            throw new ResourceNotFoundException("This User care does not belong to a Care");
        }
        User newUsers = userRepository.findById(request.getUsers().getId()).orElseThrow(() -> new ResourceNotFoundException("User with associate WorkPlace not found"));
        User newUserCared = userCaredRepository.findById(request.getUserCared().getId()).orElseThrow(() -> new ResourceNotFoundException("Market with associated WorkPlace not found"));
        care.setUser_main(newUsers);
        care.setUser_cared(newUserCared);
        Care updateCare = repository.save(care);

        return mapToRequest(updateCare);
    }

    public void deleteCare(long user_id, long market_id) {
        User users = userRepository.findById(user_id).orElseThrow(() -> new ResourceNotFoundException("User with associate WorkPlace not found"));
        User userCared = userCaredRepository.findById(user_id).orElseThrow(() -> new ResourceNotFoundException("User with associate WorkPlace not found"));
        Care care = repository.findByUserUserCared(user_id,market_id);
        if(care.getUser_main().getId()!= users.getId()) {
            throw new ResourceNotFoundException("This User does not belong to a WorkPlace");
        }
        if(care.getUser_cared().getId()!= userCared.getId()) {
            throw new ResourceNotFoundException("This User does not belong to a WorkPlace");
        }
        repository.delete(care);
    }

    private CareRequest mapToRequest(Care care) {
        CareRequest careRequest = new CareRequest();
        careRequest.setCare_id(care.getCare_id());
        careRequest.setUsers(care.getUser_main());
        careRequest.setUserCared(care.getUser_cared());
        return careRequest;
    }

    private Care mapToEntity(CareRequest request) {
        Care care = new Care();
        care.setCare_id(request.getCare_id());
        return care;
    }
}
