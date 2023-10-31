package net.javaguides.springboot.service;

import lombok.RequiredArgsConstructor;
import net.javaguides.springboot.models.Follow;
import net.javaguides.springboot.repository.FollowRepository;
import net.javaguides.springboot.request.FollowRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowService {
    private final FollowRepository repository;
    public void save(FollowRequest request){
        var follow = Follow.builder()
                .users(request.getUsers())
                .news(request.getNews())
                .build();
        repository.save(follow);


    }
    public List<Follow> findAll() {
        return repository.findAll();
    }
}
