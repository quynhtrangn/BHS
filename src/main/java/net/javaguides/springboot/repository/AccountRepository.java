package net.javaguides.springboot.repository;

import net.javaguides.springboot.models.Account;
import net.javaguides.springboot.models.Care;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account,Long> {
    Optional<Account> findById(Long account_id);

    @Query(value = "SELECT * FROM care", nativeQuery = true)
    List<Care> findAllAccount();
    @Query(value = "SELECT * FROM care WHERE user_id = :user_id", nativeQuery = true)
    List<Care> findByAccount(@Param("user_id") long user_id);


}
