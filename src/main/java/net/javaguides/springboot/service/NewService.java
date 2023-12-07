package net.javaguides.springboot.service;


import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import net.javaguides.springboot.exception.ResourceNotFoundException;
import net.javaguides.springboot.models.Account;
import net.javaguides.springboot.models.New;
import net.javaguides.springboot.repository.AccountRepository;
import net.javaguides.springboot.repository.NewRepository;
import net.javaguides.springboot.request.NewRequest;
import net.javaguides.springboot.user.User;
import net.javaguides.springboot.user.UserRepository;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NewService {
    private final NewRepository repository;
    private final AccountRepository userRepository;
    private final AccountRepository accountRepository;

    public NewRequest createNew(long users_id, NewRequest request, String originalFileName) {
        New news = mapToEntity(request,originalFileName);

        Account users = userRepository.findById(users_id).orElseThrow(() -> new ResourceNotFoundException("User with associated review not found"));

        news.setAccount(users);

        New newNew = repository.save(news);

        return mapToRequest(newNew);
    }
    public NewRequest createNews(long users_id, NewRequest request, String originalFileName) {
        New news = mapToEntity(request,originalFileName);

        Account users = userRepository.findById(users_id).orElseThrow(() -> new ResourceNotFoundException("User with associated review not found"));

        news.setAccount(users);

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
                .status(request.getStatuss())
                .build();
        repository.save(news);
    }

    public List<NewRequest> findAll() {
        List<New> markets = repository.findAll();
        return markets.stream().map(market -> mapToRequest(market)).collect(Collectors.toList());
    }

    public Resource getNewImageById(Long newId) {
        New news = repository.findById(newId)
                .orElseThrow(() -> new EntityNotFoundException("new not found"));

        // Construct the image file path based on the image name or any other logic
        String imagePath = System.getProperty("user.dir")+"/src/main/store/images/" + news.getNew_img();

        // Load the image file as a resource
        Resource imageResource = (Resource) new FileSystemResource(imagePath);

        // Perform any necessary checks or validations on the resource before returning it

        return imageResource;
    }
    public Resource getNewImageByName(String name) {
        String imagePath = System.getProperty("user.dir")+"/src/main/store/images/" + name;

        // Load the image file as a resource
        Resource imageResource = (Resource) new FileSystemResource(imagePath);

        // Perform any necessary checks or validations on the resource before returning it

        return imageResource;
    }
    public byte[] getImage(long newId) throws IOException {
        New news = repository.findById(newId)
                .orElseThrow(() -> new EntityNotFoundException("new not found"));

        // Construct the image file path based on the image name or any other logic
        String imagePath = System.getProperty("user.dir") + "/src/main/store/images/" + news.getNew_img();

        // Read the image file as byte array
        Path path = Paths.get(imagePath);
        byte[] imageData = Files.readAllBytes(path);

        // Perform any necessary checks or validations on the image data before returning it

        return imageData;
    }

    public List<NewRequest> getNewByUser(long id) {

        List<New> markets = repository.findNewByUser(id);
        return markets.stream().map(market -> mapToRequest(market)).collect(Collectors.toList());
    }
    public NewRequest getNewByid(long id) {

        New markets = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("City could not be found"));
        return mapToRequest(markets);
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



    public NewRequest updateNew(long new_id, long users_id, NewRequest request,String originalFileName) {
        New news = repository.findById(new_id).orElseThrow(() -> new ResourceNotFoundException("New with associated users not found"));

        Account users = userRepository.findById(users_id).orElseThrow(() -> new ResourceNotFoundException("User with associate market not found"));

        if(news.getAccount().getAccount_id() != users.getAccount_id()) {
            throw new ResourceNotFoundException("This User does not belong to a New");
        }

        news.setNew_name(request.getNew_name());
        news.setNew_img(originalFileName);
        news.setNew_field(request.getNew_field());
        news.setNew_description(request.getNew_description());
        news.setQuantity(request.getQuantity());
        news.setCost(request.getCost());
        news.setStart_time(request.getStart_time());
        news.setEnd_time(request.getEnd_time());
        news.setStatus(request.getStatuss());
        news.setAccount(users);
        New updateNew = repository.save(news);

        return mapToRequest(updateNew);
    }


    public NewRequest getNewById(long id) {
        New market = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("New could not be found"));
        return mapToRequest(market);
    }

    private New mapToEntity(NewRequest request,String originalFileName) {
        New news = new New();

        news.setNew_id(request.getNew_id());
        news.setNew_name(request.getNew_name());
        news.setNew_img(originalFileName);
        news.setNew_field(request.getNew_field());
        news.setNew_description(request.getNew_description());
        news.setQuantity(request.getQuantity());
        news.setCost(request.getCost());
        news.setStart_time(request.getStart_time());
        news.setEnd_time(request.getEnd_time());
        news.setStatus(request.getStatuss());
        news.setAccount(request.account);
        return news;
    }
    private New mapToEntitys(NewRequest request,String originalFileName) {
        New news = new New();

        news.setNew_id(request.getNew_id());
        news.setNew_name(request.getNew_name());
        news.setNew_img(originalFileName);
        news.setNew_field(request.getNew_field());
        news.setNew_description(request.getNew_description());
        news.setQuantity(request.getQuantity());
        news.setCost(request.getCost());
        news.setStart_time(request.getStart_time());
        news.setEnd_time(request.getEnd_time());
        news.setStatus(request.getStatuss());
        news.setAccount(request.account);
        return news;
    }
    private NewRequest mapToRequest(New news) {

        NewRequest request = new NewRequest();
        request.setNew_id(news.getNew_id());
        request.setNew_name(news.getNew_name());
        request.setNew_field(news.getNew_field());
        request.setLinkImg(news.getNew_img());
        request.setNew_description(news.getNew_description());
        request.setQuantity(news.getQuantity());
        request.setCost(news.getCost());
        request.setStart_time(news.getStart_time());
        request.setEnd_time(news.getEnd_time());
        request.setStatuss(news.getStatus());
        request.setAccount(news.account);

        return request;
    }

}
