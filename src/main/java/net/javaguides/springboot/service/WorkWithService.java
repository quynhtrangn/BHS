package net.javaguides.springboot.service;

import lombok.RequiredArgsConstructor;
import net.javaguides.springboot.models.WorkWith;
import net.javaguides.springboot.repository.WorkWithRepository;
import net.javaguides.springboot.request.WorkWithRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkWithService {
    private final WorkWithRepository repository;
    public void save(WorkWithRequest request){
        var workWith = WorkWith.builder()
                .users(request.getUsers())
                .field(request.getField())
                .build();
        repository.save(workWith);


    }
    public List<WorkWith> findAll() {
        return repository.findAll();
    }
}