package net.javaguides.springboot.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import net.javaguides.springboot.user.User;


import java.io.Serializable;
import java.security.Timestamp;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="new")
@Builder
public class New implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long new_id;

    @Column(name = "new_name")
    private String new_name;

    @Column(name = "new_field")
    private String new_field;

    @Column(name = "new_img")
    private String new_img;

    @Column(name = "new_description")
    private String new_description;

    @Column(name = "quantity")
    private Long quantity;

    @Column(name = "cost")
    private float cost;

    @Column(name = "start_time")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime start_time;

    @Column(name = "end_time")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime end_time;

    @Column(name = "status")
    private int status;


    @ManyToOne(fetch = FetchType.EAGER,optional = false)
    @JoinColumn(name = "user_id")
    public Account account;

    @JsonIgnore
    @OneToMany(mappedBy = "news")
    private Set<Follow> follows = new HashSet<Follow>();

    @Override
    public String toString() {
        return "New{" +
                "new_id=" + new_id +
                ", new_name='" + new_name + '\'' +
                ", new_img='" + new_img + '\'' +
                ", new_description='" + new_description + '\'' +
                ", quantity=" + quantity +
                ", cost=" + cost +
                ", start_time=" + start_time +
                ", end_time=" + end_time +
                ", status=" + status +
                ", users=" + account +
                ", follows=" + follows +
                '}';
    }
}
