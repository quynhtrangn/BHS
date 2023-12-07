package net.javaguides.springboot.auth;

import org.springframework.core.io.Resource;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import net.javaguides.springboot.exception.ResourceNotFoundException;
import net.javaguides.springboot.request.NewRequest;
import net.javaguides.springboot.service.UserService;
import net.javaguides.springboot.user.User;
import net.javaguides.springboot.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.io.FilenameUtils;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin
public class AuthenticationController {
    private final AuthenticationService service;
    @Autowired
    private UserRepository userRepository;

    public static String uploadDirectory =
            System.getProperty("user.dir")+"/src/main/store/images";

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request

    ){
        if(userRepository.findByTel(request.getTel())==null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(service.register(request));
    }



    @GetMapping("/{userId}/image")
    public ResponseEntity<Resource> getUserImage(@PathVariable Long userId) {
        // Retrieve the image file from the UserService
        Resource imageResource = service.getUserImageById(userId);

        // Get the file name with extension from the resource
        String fileName = imageResource.getFilename();

        // Determine the media type based on the file extension
        MediaType mediaType = MediaType.IMAGE_JPEG; // Default media type
        String fileExtension = FilenameUtils.getExtension(fileName).toLowerCase();
        if (fileExtension.equals("png")) {
            mediaType = MediaType.IMAGE_PNG;
        } else if (fileExtension.equals("gif")) {
            mediaType = MediaType.IMAGE_GIF;
        }
        // Add more if statements for other supported image formats

        // Set the appropriate headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(mediaType);

        // Return the image file in the response
        return new ResponseEntity<>(imageResource, headers, HttpStatus.OK);
    }

    @GetMapping("/accounts")
    public String listUsers(Model model){
        model.addAttribute("accounts",service.findAll()).toString();
        return "index";
    }


    @GetMapping("/getAllAccount")
    public List<User> findAllAccount() {
        return service.findAll();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") long user_id) {
        service.deleteAccount(user_id);
        return new ResponseEntity<>("User delete", HttpStatus.OK);
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
