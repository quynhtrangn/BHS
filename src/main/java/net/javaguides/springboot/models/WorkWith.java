package net.javaguides.springboot.models;

import jakarta.persistence.*;
import lombok.*;
import net.javaguides.springboot.user.User;


@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="work_with")
@Builder

public class WorkWith {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long workwith_id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User users;

    @ManyToOne
    @JoinColumn(name = "field_id")
    private Field field;
}
