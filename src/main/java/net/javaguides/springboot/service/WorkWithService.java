package net.javaguides.springboot.service;

import lombok.RequiredArgsConstructor;
import net.javaguides.springboot.exception.ResourceNotFoundException;
import net.javaguides.springboot.models.Field;
import net.javaguides.springboot.models.Market;
import net.javaguides.springboot.models.WorkWith;
import net.javaguides.springboot.repository.FieldRepository;
import net.javaguides.springboot.repository.WorkWithRepository;
import net.javaguides.springboot.request.WorkWithRequest;
import net.javaguides.springboot.user.User;
import net.javaguides.springboot.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WorkWithService {
    private final WorkWithRepository repository;
    private final FieldRepository fieldRepository;
    private final UserRepository userRepository;
    public void save(WorkWithRequest request){
        var workWith = WorkWith.builder()
                .users(request.getUsers())
                .field(request.getField())
                .build();
        repository.save(workWith);


    }
    public WorkWithRequest createWorkWith(long user_id, long field_id, WorkWithRequest request) {
        WorkWith workWith = mapToEntity(request);

        Field field = fieldRepository.findById(field_id).orElseThrow(() -> new ResourceNotFoundException("Field with associated review not found"));
        User users = userRepository.findById(user_id).orElseThrow(() -> new ResourceNotFoundException("User with associated review not found"));
        workWith.setField(field);
        workWith.setUsers(users);

        WorkWith newWorkWith = repository.save(workWith);

        return mapToRequest(newWorkWith);
    }
    public List<WorkWithRequest> findAllWorkWith() {
        List<WorkWith> workWiths = repository.findAllWorkWith();
        return workWiths.stream().map(workWith -> mapToRequest(workWith)).collect(Collectors.toList());
    }

    public List<WorkWithRequest> getWorkWithByUser(long id) {
        List<WorkWith> workWiths = repository.findByUser(id);
        return workWiths.stream().map(workWith -> mapToRequest(workWith)).collect(Collectors.toList());
    }
    public List<WorkWith> findAll() {
        return repository.findAll();
    }

    public List<WorkWithRequest> getWorkWithByField(long id) {

        List<WorkWith> workWiths = repository.findByField(id);
        return workWiths.stream().map(workWith -> mapToRequest(workWith)).collect(Collectors.toList());
    }
    public WorkWithRequest getWorkWithByUserField(long user_id, long field_id) {
        WorkWith workWiths = repository.findByUserField(user_id,field_id);
        return mapToRequest(repository.findByUserField(user_id,field_id));
    }

    public WorkWithRequest updateWorkWith(long user_id, long field_id, WorkWithRequest request) {
        WorkWith workWith = repository.findByUserField(user_id,field_id);
        User users = userRepository.findById(user_id).orElseThrow(() -> new ResourceNotFoundException("User with associate WorkWith not found"));
        Field field = fieldRepository.findById(field_id).orElseThrow(() -> new ResourceNotFoundException("Field with associated WorkWith not found"));

        if(workWith.getUsers().getId()!= users.getId()) {
            throw new ResourceNotFoundException("This User does not belong to a WorkWith");
        }
        if(workWith.getField().getField_id()!= field.getField_id()) {
            throw new ResourceNotFoundException("This Field does not belong to a WorkWith");
        }
        User newUsers = userRepository.findById(request.getUsers().getId()).orElseThrow(() -> new ResourceNotFoundException("User with associate WorkPlace not found"));
        Field newField = fieldRepository.findById(request.getField().getField_id()).orElseThrow(() -> new ResourceNotFoundException("Market with associated WorkPlace not found"));
        workWith.setUsers(newUsers);
        workWith.setField(newField);

        WorkWith updateWorkWith = repository.save(workWith);

        return mapToRequest(updateWorkWith);
    }

    public void deleteWorkWith(long user_id, long field_id) {
        User users = userRepository.findById(user_id).orElseThrow(() -> new ResourceNotFoundException("User with associate WorkWith not found"));
        Field field = fieldRepository.findById(field_id).orElseThrow(() -> new ResourceNotFoundException("Field with associated WorkWith not found"));
        WorkWith workWith = repository.findByUserField(user_id,field_id);
        if(workWith.getUsers().getId()!= users.getId()) {
            throw new ResourceNotFoundException("This User does not belong to a WorkWith");
        }
        if(workWith.getField().getField_id()!= field.getField_id()) {
            throw new ResourceNotFoundException("This Field does not belong to a WorkWith");
        }
        repository.delete(workWith);
    }

    private WorkWithRequest mapToRequest(WorkWith workWith) {
        WorkWithRequest workWithRequest = new WorkWithRequest();
        workWithRequest.setWorkwith_id(workWith.getWorkwith_id());
        workWithRequest.setUsers(workWith.getUsers());
        workWithRequest.setField(workWith.getField());
        return workWithRequest;
    }

    private WorkWith mapToEntity(WorkWithRequest request) {
        WorkWith workWith = new WorkWith();
        workWith.setWorkwith_id(request.getWorkwith_id());
        return workWith;
    }
}