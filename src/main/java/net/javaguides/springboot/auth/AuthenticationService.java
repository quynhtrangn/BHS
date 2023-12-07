package net.javaguides.springboot.auth;

import net.javaguides.springboot.models.Account;
import net.javaguides.springboot.repository.AccountRepository;
import net.javaguides.springboot.request.AccountRequest;
import org.springframework.core.io.Resource;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import net.javaguides.springboot.config.JwtService;

import net.javaguides.springboot.exception.ResourceNotFoundException;
import net.javaguides.springboot.models.City;
import net.javaguides.springboot.models.New;
import net.javaguides.springboot.request.CityRequest;
import net.javaguides.springboot.service.UserService;
import net.javaguides.springboot.user.Role;
import net.javaguides.springboot.user.User;
import net.javaguides.springboot.user.UserRepository;
import net.javaguides.springboot.token.Token;
import net.javaguides.springboot.token.TokenRepository;
import net.javaguides.springboot.token.TokenType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final AccountRepository accountRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;



    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .emailId(request.getEmailId())
                .city(request.getCity())
                .tel(request.getTel())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();
        var savedUser = repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        Account account = mapToEntity(user);
        accountRepository.save(account);
        saveUserToken(savedUser, jwtToken);

        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getTel(),
                        request.getPassword()
                )
        );
        var user = repository.findByTel(request.getTel())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }
    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser((int) user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }
    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);


    }
    public List<User> findAll() {
        return repository.findAll();
    }

    public Resource getUserImageById(Long userId) {
        User user = repository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        // Construct the image file path based on the image name or any other logic
        String imagePath = System.getProperty("user.dir")+"/src/main/store/images/" + user.getUser_img();

        // Load the image file as a resource
        Resource imageResource = (Resource) new FileSystemResource(imagePath);

        // Perform any necessary checks or validations on the resource before returning it

        return imageResource;
    }

    public String getFileNameFromResource(Resource resource) {
        try {
            File file = resource.getFile();
            return file.getName();
        } catch (IOException e) {
            // Handle the exception appropriately
            return null;
        }
    }


    public boolean deleteAccount(long id){
        if(id>=1){
            User users = repository.getById(id);
            if(users!=null){
                repository.delete(users);
                return true;
            }
        }
        return false;
    }
    private Account mapToEntity(User request) {
        Account account = new Account();
        account.setAccount_id(request.getId());
        account.setFirstName(request.getFirstName());
        account.setLastName(request.getLastName());
        account.setRole(request.getRole());
        account.setPhone(request.getTel());
        account.setAccount_img(request.getUser_img());
        account.setEmailId(request.getEmailId());
        account.setStatuss(1);
        account.setUser(request);

        return account;
    }
}
