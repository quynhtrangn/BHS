package net.javaguides.springboot.request;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import net.javaguides.springboot.models.Account;
import net.javaguides.springboot.repository.AccountRepository;
import net.javaguides.springboot.user.User;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewRequest {
    private long new_id;
    private String new_name;
    private MultipartFile new_img;
    private String new_field;
    private String linkImg;
    private String new_description;
    private Long quantity;
    private float cost;
    private LocalDateTime start_time;
    private LocalDateTime end_time;
    private int statuss;
    public Account account;

}
