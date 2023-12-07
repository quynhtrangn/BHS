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
@Table(name="follow")
@Builder

public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long follow_id;

    @ManyToOne(fetch = FetchType.EAGER,optional = false)
    @JsonIgnore
    @JoinColumn(name = "users_id")
    private User users;

    @ManyToOne(fetch = FetchType.EAGER,optional = false)

    @JoinColumn(name = "new_id")
    private New news;
}
