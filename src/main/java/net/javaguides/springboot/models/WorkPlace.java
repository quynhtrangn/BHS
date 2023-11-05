package net.javaguides.springboot.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import net.javaguides.springboot.user.User;

import java.io.Serializable;


@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="work_place")
@Builder
public class WorkPlace implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long workplace_id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER,optional = false)
    @JoinColumn(name = "user_id")
    public User users;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER,optional = false)
    @JoinColumn(name = "market_id")
    public Market market;

    @Override
    public String toString() {
        return "WorkPlace{" +
                "workplace_id=" + workplace_id +
                ", user=" + users +
                ", market=" + market +
                '}';
    }
}
