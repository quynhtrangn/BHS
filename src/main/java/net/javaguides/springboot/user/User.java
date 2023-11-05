package net.javaguides.springboot.user;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import net.javaguides.springboot.models.*;
import net.javaguides.springboot.token.Token;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="user_s")
@Builder
public class User implements UserDetails, Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "first_name")
    @NotBlank(message = "Không được để trống")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @NotBlank(message = "Không được để trống")
    @Pattern(regexp ="^[a-z0-9](\\.?[a-z0-9]){5,}@g(oogle)?mail\\.com$",message="Email không hợp lệ")
    @Column(name = "email-id")
    private String emailId;


    @Column(name = "city")
    private String city;

    @Column(name = "user_img")
    private String user_img;

    @Column(name = "tel", unique = true)
    private String tel;

    @Column(name = "password")
    @NotBlank(message = "Không được để trống")
    @Size(min=6,message = "Yêu cầu mật khẩu từ 6 kí tự trở lên")
    private String password;

    @Column(name = "role" )
    @Enumerated(EnumType.STRING)
    private Role role;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Token> tokens;

//    @OneToMany(mappedBy = "user")
//    private List<Care> cares;

//    @JsonIgnore
//    @ManyToMany(cascade={CascadeType.ALL})
//    @JoinTable(name="care",
//            joinColumns={@JoinColumn(name="user_id")},
//            inverseJoinColumns={@JoinColumn(name="user_cared")})
//    private Set<User> usercared = new HashSet<User>();

    @JsonIgnore
    @OneToMany(mappedBy = "user_main")
    private List<Care> care;

    @JsonIgnore
    @OneToMany(mappedBy = "user_cared")
    private List<Care> cared;

//    @JsonIgnore
//    @ManyToMany(mappedBy="usercared")
//    private Set<User> users = new HashSet<User>();
//    @OneToMany(mappedBy = "user")
//    private List<Care> cared;
//
    @JsonIgnore
    @OneToMany(mappedBy = "users")
    private List<WorkWith> workWiths;

    @JsonIgnore
    @OneToMany(mappedBy = "users")
    private List<New> news;

    @JsonIgnore
    @OneToMany(mappedBy = "users")
    private List<Follow> follows;

    @JsonIgnore
    @OneToMany(mappedBy = "users")
    private List<WorkPlace> workPlaces;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Location> locations;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return tel;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", emailId='" + emailId + '\'' +
                ", city='" + city + '\'' +
                ", user_img='" + user_img + '\'' +
                ", tel='" + tel + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", tokens=" + tokens +
                ", care=" + care +
                ", cared=" + cared +
                ", workWiths=" + workWiths +
                ", news=" + news +
                ", follows=" + follows +
                ", workPlaces=" + workPlaces +
                ", locations=" + locations +
                '}';
    }
}
