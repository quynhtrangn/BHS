package net.javaguides.springboot.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import net.javaguides.springboot.user.User;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="care")
@Builder
public class Care {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long care_id;

    @ManyToOne(fetch = FetchType.EAGER,optional = false)
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private User user_main;

    @ManyToOne(fetch = FetchType.EAGER,optional = false)
    @JsonIgnore
    @JoinColumn(name = "user_cared_id")
    private User user_cared;
}
