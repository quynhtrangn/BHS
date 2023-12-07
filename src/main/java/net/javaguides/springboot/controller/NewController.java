package net.javaguides.springboot.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import net.javaguides.springboot.models.New;
import net.javaguides.springboot.request.NewRequest;
import net.javaguides.springboot.service.NewService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/auth/news")
@RequiredArgsConstructor
@CrossOrigin
public class NewController {
    private final NewService service;
    public static String uploadDirectory =
            System.getProperty("user.dir")+"/src/main/store/images";

    @GetMapping("/{id}")
    public ResponseEntity<?> newsDetail(@PathVariable long id) {
        return ResponseEntity.ok(service.getNewById(id));
    }

    @PostMapping("/new/{user_id}/user")
    public ResponseEntity<NewRequest> createReview(
            @PathVariable("user_id")long user_id,
            @ModelAttribute NewRequest request,
            @RequestParam("new_img") MultipartFile file)
   throws IOException
    {
        String originalFileName = file.getOriginalFilename();
        Path fileNamePath = Paths.get(uploadDirectory,originalFileName);
        Files.write(fileNamePath,file.getBytes());
        System.out.println(originalFileName);
        return new ResponseEntity<>(service.createNew(user_id, request,originalFileName), HttpStatus.CREATED);
    }
    @PostMapping("/{user_id}/create")
    public ResponseEntity<NewRequest> createNew(
            @PathVariable("user_id") long user_id,
            @ModelAttribute NewRequest request)
            throws IOException {
        // Access the new_img field from the request object if needed
        MultipartFile file = request.getNew_img();

        // Generate a random file name
        String randomFileName = generateRandomFileName(file.getOriginalFilename());

        Path fileNamePath = Paths.get(uploadDirectory, randomFileName);
        Files.write(fileNamePath, file.getBytes());

        // Generate the public URL for the image
        String publicUrl = String.valueOf(getUserImageLink(randomFileName));

        return new ResponseEntity<>(service.createNews(user_id, request, publicUrl), HttpStatus.CREATED);
    }

    private String generateRandomFileName(String originalFilename) {
        String fileExtension = getFileExtension(originalFilename);
        String randomName = UUID.randomUUID().toString();
        return randomName + fileExtension;
    }

    private String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            return fileName.substring(dotIndex);
        }
        return "";
    }

    public String getUserImageLinks(String fileName) {
        // Generate the public URL for the image
        String publicUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/v1/auth/news/")
                .path(fileName)
                .path("/image")
                .toUriString();

        // Return the public URL
        return publicUrl;
    }
    @GetMapping("/getAllNews")
    public List<NewRequest> findAllNews() {
        return service.findAll();
    }

    @GetMapping("/getNewByUser/{id}")
    public List<NewRequest> findNewByUser(
            @PathVariable("id")long user_id

    ) {

        return  service.getNewByUser(user_id);
    }
    @GetMapping("/{name}/image")
    public ResponseEntity<Resource> getUserImage(@PathVariable String name) {
        // Retrieve the image file from the UserService
        Resource imageResource = service.getNewImageByName(name);

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

//    @GetMapping("/news/{newId}/image-link")
    public String getUserImageLink(@PathVariable String newId) {
        // Retrieve the image file from the UserService
        Resource imageResource = service.getNewImageByName(newId);

        // Get the file name with extension from the resource
        String fileName = imageResource.getFilename();

        // Generate the public URL for the image
        String publicUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/v1/auth/news/")
                .path(String.valueOf(newId))
                .path("/image")
                .toUriString();

        // Return the public URL as the API response
        return publicUrl;
    }

    @GetMapping(value = "/image/{id}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_GIF_VALUE})
    public ResponseEntity<byte[]> getImage(@PathVariable("id") long id, HttpServletResponse response) throws IOException {
        byte[] image = service.getImage(id); // Retrieve the image data from the imageService

        String contentType = determineContentType(String.valueOf(id)); // Determine the content type based on the file extension
        response.setContentType(contentType);

        return ResponseEntity.ok(image);
    }

    private String determineContentType(String id) {
        // Extract the file extension from the id
        String extension = id.substring(id.lastIndexOf('.') + 1).toLowerCase();

        switch (extension) {
            case "png":
                return MediaType.IMAGE_PNG_VALUE;
            case "gif":
                return MediaType.IMAGE_GIF_VALUE;
            // Add more cases for other image formats if needed
            default:
                return MediaType.IMAGE_JPEG_VALUE;
        }
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteNew(@PathVariable("id") long new_id) {
        service.deleteNew(new_id);
        return new ResponseEntity<>("New delete", HttpStatus.OK);
    }



    @PutMapping("/{new_id}/user/{user_id}")
    public ResponseEntity<?> updateNew(@PathVariable(value = "new_id") long new_id, @PathVariable(value = "user_id") long user_id,
                                          @ModelAttribute NewRequest request

        ) throws IOException {

        MultipartFile file = request.getNew_img();

        // Generate a random file name
        String randomFileName = generateRandomFileName(file.getOriginalFilename());

        Path fileNamePath = Paths.get(uploadDirectory, randomFileName);
        Files.write(fileNamePath, file.getBytes());

        // Generate the public URL for the image
        String publicUrl = String.valueOf(getUserImageLink(randomFileName));


        NewRequest updatedNew = service.updateNew(new_id,user_id,request,publicUrl);
        return new ResponseEntity<>(updatedNew, HttpStatus.OK);
    }
}
