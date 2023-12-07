package net.javaguides.springboot.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name="work_with")
@Builder

public class WorkWith implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long workwith_id;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "user_id")
    public User users;

    @ManyToOne
    @JoinColumn(name = "field_id")
    private Field field;

    @Override
    public String toString() {
        return "WorkWith{" +
                "workwith_id=" + workwith_id +
                ", user=" + users +
                ", field=" + field +
                '}';
    }
}
