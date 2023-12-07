package net.javaguides.springboot.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import net.javaguides.springboot.user.Role;
import net.javaguides.springboot.user.User;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="account")
@Builder
public class Account  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long account_id;

    @Column(name = "first_name")
    @NotBlank(message = "Không được để trống")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "account_img" )
    private String account_img;

    @Column(name = "emailId")
    private String emailId;

    @Column(name = "phone")
    private String phone;

    @Column(name = "statuss" )
    private int statuss;

    @Column(name = "role" )
    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToOne(fetch = FetchType.EAGER,optional = false)
    @JsonIgnore
    @JoinColumn(name = "user_id")
    public User user;

    @JsonIgnore
    @OneToMany(mappedBy = "account")
    private Set<New> news = new HashSet<New>();
}
