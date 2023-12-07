package net.javaguides.springboot.request;

import lombok.*;
import net.javaguides.springboot.models.*;
import net.javaguides.springboot.user.Role;
import net.javaguides.springboot.user.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountRequest {
    private long id;
    private String firstName;
    private String lastName;
    private MultipartFile account_img;
    private String imgLink;
    private String emailId;
    private String phone;
    private Role role;
    private int statuss;
    public User users;
    public List<New> news;
    public List<WorkPlace> workPlaces;
    public List<Follow> follows;
    public List<WorkWith> workWiths;
    public List<Care> cares;
    public long user_id;
}
