package net.javaguides.springboot.request;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import net.javaguides.springboot.user.User;

import java.security.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LocationRequest {
    private long location_id;
    private LocalDateTime start_time;
    private LocalDateTime end_time;
    private int status;
    public User user;
}
