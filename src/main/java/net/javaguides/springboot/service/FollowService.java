package net.javaguides.springboot.service;

import lombok.RequiredArgsConstructor;
import net.javaguides.springboot.exception.ResourceNotFoundException;
import net.javaguides.springboot.models.Follow;
import net.javaguides.springboot.models.New;
import net.javaguides.springboot.models.Follow;
import net.javaguides.springboot.repository.FollowRepository;
import net.javaguides.springboot.repository.NewRepository;
import net.javaguides.springboot.repository.NewRepository;
import net.javaguides.springboot.request.FollowRequest;
import net.javaguides.springboot.request.FollowRequest;
import net.javaguides.springboot.user.User;
import net.javaguides.springboot.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FollowService {
    private final FollowRepository repository;
    private final NewRepository newRepository;
    private final UserRepository userRepository;
    
    public void save(FollowRequest request){
        var follow = Follow.builder()
                .users(request.getUsers())
                .news(request.getNews())
                .build();
        repository.save(follow);


    }
    public FollowRequest createFollow(long user_id, long news_id, FollowRequest request) {
        Follow follow = mapToEntity(request);

        New news = newRepository.findById(news_id).orElseThrow(() -> new ResourceNotFoundException("New with associated review not found"));
        User users = userRepository.findById(user_id).orElseThrow(() -> new ResourceNotFoundException("User with associated review not found"));
        follow.setNews(news);;
        follow.setUsers(users);

        Follow newFollow = repository.save(follow);

        return mapToRequest(newFollow);
    }

    public List<FollowRequest> findAll() {
        List<Follow> follows = repository.findAll();
        return follows.stream().map(follow -> mapToRequest(follow)).collect(Collectors.toList());
    }

    public List<FollowRequest> getFollowByUser(long id) {
        List<Follow> follows = repository.findByUser(id);
        return follows.stream().map(follow -> mapToRequest(follow)).collect(Collectors.toList());
    }

    public List<FollowRequest> getFollowByNew(long id) {

        List<Follow> follows = repository.findByNew(id);
        return follows.stream().map(follow -> mapToRequest(follow)).collect(Collectors.toList());
    }
    public FollowRequest getFollowByUserNew(long user_id, long news_id) {
        Follow follows = repository.findByUserNew(user_id,news_id);
        return mapToRequest(follows);
    }

    public FollowRequest updateFollow(long user_id, long news_id, FollowRequest request) {
        Follow follow = repository.findByUserNew(user_id,news_id);
        User users = userRepository.findById(user_id).orElseThrow(() -> new ResourceNotFoundException("User with associate Follow not found"));
        New news = newRepository.findById(news_id).orElseThrow(() -> new ResourceNotFoundException("New with associated Follow not found"));

        if(follow.getUsers().getId()!= users.getId()) {
            throw new ResourceNotFoundException("This User does not belong to a Follow");
        }
        if(follow.getNews().getNew_id()!= news.getNew_id()) {
            throw new ResourceNotFoundException("This New does not belong to a Follow");
        }
        User newUsers = userRepository.findById(request.getUsers().getId()).orElseThrow(() -> new ResourceNotFoundException("User with associate Follow not found"));
        New newNew = newRepository.findById(request.getNews().getNew_id()).orElseThrow(() -> new ResourceNotFoundException("New with associated Follow not found"));
        follow.setUsers(newUsers);
        follow.setNews(newNew);
        Follow updateFollow = repository.save(follow);

        return mapToRequest(updateFollow);
    }

    public void deleteFollow(long user_id, long news_id) {
        User users = userRepository.findById(user_id).orElseThrow(() -> new ResourceNotFoundException("User with associate Follow not found"));
        New news = newRepository.findById(news_id).orElseThrow(() -> new ResourceNotFoundException("New with associated Follow not found"));
        Follow follow = repository.findByUserNew(user_id,news_id);
        if(follow.getUsers().getId()!= users.getId()) {
            throw new ResourceNotFoundException("This User does not belong to a Follow");
        }
        if(follow.getNews().getNew_id()!= news.getNew_id()) {
            throw new ResourceNotFoundException("This New does not belong to a Follow");
        }
        repository.delete(follow);
    }

    private FollowRequest mapToRequest(Follow follow) {
        FollowRequest followRequest = new FollowRequest();
        followRequest.setFollow_id(follow.getFollow_id());
        followRequest.setUsers(follow.getUsers());
        followRequest.setNews(follow.getNews());
        return followRequest;
    }

    private Follow mapToEntity(FollowRequest request) {
        Follow follow = new Follow();
        follow.setFollow_id(request.getFollow_id());
        request.setUsers(follow.getUsers());
        request.setNews(follow.getNews());
        return follow;
    }
}
