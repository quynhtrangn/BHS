package net.javaguides.springboot.service;

import lombok.RequiredArgsConstructor;
import net.javaguides.springboot.exception.ResourceNotFoundException;
import net.javaguides.springboot.models.*;
import net.javaguides.springboot.repository.*;
import net.javaguides.springboot.request.AccountRequest;
import net.javaguides.springboot.request.CareRequest;
import net.javaguides.springboot.request.NewRequest;
import net.javaguides.springboot.user.User;
import net.javaguides.springboot.user.UserRepository;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository repository;
    private final UserRepository userRepository;
    private final NewRepository newRepository;
    private final WorkPlaceRepository workPalaceRepository;
    private final FollowRepository followRepository;
    private final WorkWithRepository workWithRepository;
    private final CareRepository careRepository;


    public AccountRequest getNewById(long id) {
        Account account = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Account could not be found"));
        return mapToRequest(account);
    }

    public AccountRequest createAccount(long users_id, AccountRequest request, String originalFileName) {
        Account account = mapToEntity(request,originalFileName);

        User user = userRepository.findById(users_id).orElseThrow(() -> new ResourceNotFoundException("User with associated review not found"));

        account.setUser(user);

        Account newAccount = repository.save(account);

        return mapToRequest(newAccount);
    }
    public Resource getAccountImageByName(String name) {
        String imagePath = System.getProperty("user.dir")+"/src/main/store/images/" + name;

        // Load the image file as a resource
        Resource imageResource = (Resource) new FileSystemResource(imagePath);

        // Perform any necessary checks or validations on the resource before returning it

        return imageResource;
    }

    public List<AccountRequest> findAll() {
        List<Account> markets = repository.findAll();
        return markets.stream().map(market -> mapToRequest(market)).collect(Collectors.toList());
    }

    public boolean deleteAccount(long id){
        if(id>=1){
            Account news = repository.getById(id);
            if(news!=null){
                repository.delete(news);
                return true;
            }
        }
        return false;
    }

    public AccountRequest updateAccount(long new_id, long users_id, AccountRequest request,String originalFileName) {
        Account account = repository.findById(new_id).orElseThrow(() -> new ResourceNotFoundException("New with associated users not found"));

        User users = userRepository.findById(users_id).orElseThrow(() -> new ResourceNotFoundException("User with associate market not found"));

        if(account.getUser().getId() != users.getId()) {
            throw new ResourceNotFoundException("This User does not belong to a New");
        }

        account.setFirstName(request.getFirstName());
        account.setLastName(request.getLastName());
        account.setAccount_img(originalFileName);
        account.setEmailId(request.getEmailId());
        account.setPhone(request.getPhone());
        account.setRole(request.getRole());
        account.setStatuss(request.getStatuss());
        account.setUser(users);
        Account updateAccount = repository.save(account);

        return mapToRequest(updateAccount);
    }

    private Account mapToEntity(AccountRequest request,String originalFileName) {
        Account account = new Account();

        account.setAccount_id(request.getId());
        account.setFirstName(request.getFirstName());
        account.setLastName(request.getLastName());
        account.setAccount_img(originalFileName);
        account.setRole(request.getRole());
        account.setEmailId(request.getEmailId());
        account.setPhone(request.getPhone());
        account.setStatuss(request.getStatuss());
        account.setUser(request.users);

        return account;
    }

    private AccountRequest mapToRequest(Account account) {

        AccountRequest request = new AccountRequest();
        request.setId(account.getAccount_id());
        request.setFirstName(account.getFirstName());
        request.setLastName(account.getLastName());
        request.setImgLink(account.getAccount_img());
        request.setEmailId(account.getEmailId());
        request.setStatuss(account.getStatuss());
        request.setPhone(account.getPhone());
        request.setUser_id(account.getUser().getId());
        request.setStatuss(account.getStatuss());
        request.setRole(account.getRole());
        request.setUsers(account.getUser());
        request.setNews(newRepository.findNewByUser(account.getUser().getId()));
        request.setWorkPlaces(workPalaceRepository.findByUser(account.getUser().getId()));
        request.setFollows(followRepository.findByUser(account.getUser().getId()));
        request.setWorkWiths(workWithRepository.findByUser(account.getUser().getId()));
        request.setCares(careRepository.findByUser(account.getUser().getId()));

        return request;
    }
}
