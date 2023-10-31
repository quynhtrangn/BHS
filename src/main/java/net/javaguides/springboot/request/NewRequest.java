package net.javaguides.springboot.request;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import net.javaguides.springboot.user.User;

import java.security.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class NewRequest {
    private long new_id;
    private String new_name;
    private Long quantity;
    private float cost;
    private LocalDateTime start_time;
    private LocalDateTime end_time;
    private int status;
    public User users;
}
