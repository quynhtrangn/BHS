package net.javaguides.springboot.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import net.javaguides.springboot.user.User;


import java.security.Timestamp;
import java.time.LocalDateTime;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="location")
@Builder
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long location_id;

    @Column(name = "start_time")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime start_time;

    @Column(name = "end_time")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime end_time;

    @Column(name = "status")
    private int status;

    @ManyToOne(fetch = FetchType.EAGER,optional = false)
    @JsonIgnore
    @JoinColumn(name = "user_id")
    public User user;

}
