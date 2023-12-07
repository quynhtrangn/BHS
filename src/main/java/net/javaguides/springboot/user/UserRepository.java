package net.javaguides.springboot.user;

import org.springframework.data.jpa.repository.JpaRepository;
import net.javaguides.springboot.user.User;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findById(Long id);


    Optional<User> findByFirstName(String firstName);
    Optional<User> findByEmailId(String email_id);
    Optional<User> findByTel(String tel);

    @Query(value = "SELECT * FROM user_s WHERE tel = :tel", nativeQuery = true)
    User findUserByTel(String tel);
}
