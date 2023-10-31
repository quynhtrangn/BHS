package net.javaguides.springboot.token;

import jakarta.persistence.*;
import lombok.*;
import net.javaguides.springboot.user.User;
@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "token")
@Entity
public class Token {

    @Id
    @GeneratedValue
    public Integer token_id;

    @Column(unique = true)
    public String token;

    @Enumerated(EnumType.STRING)
    public TokenType tokenType = TokenType.BEARER;

    public boolean revoked;

    public boolean expired;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    public User user;
}