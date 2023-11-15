package net.javaguides.springboot.auth;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import net.javaguides.springboot.exception.ResourceNotFoundException;
import net.javaguides.springboot.user.User;
import net.javaguides.springboot.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin
public class AuthenticationController {
    private final AuthenticationService service;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request

    ){
        if(userRepository.findByTel(request.getTel())==null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(service.register(request));
    }

    @GetMapping("/accounts")
    public String listUsers(Model model){
        model.addAttribute("accounts",service.findAll()).toString();
        return "index";
    }

    @GetMapping("{id}")
    public ResponseEntity<User> getEmployeeById(@PathVariable long id){
        User user = userRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Employee not exits with id:"+id));
        return ResponseEntity.ok(user);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ){
        return ResponseEntity.ok(service.authenticate(request));
    }


}
