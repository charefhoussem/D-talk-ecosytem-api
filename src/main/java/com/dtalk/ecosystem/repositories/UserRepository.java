package com.dtalk.ecosystem.repositories;

import com.dtalk.ecosystem.entities.Role;
import com.dtalk.ecosystem.entities.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);
    List<User> findByRole(Role role);
    @Query("SELECT u FROM User u WHERE u.codeVerification = ?1")
    User findByVerificationCode(String code);

    Optional<User> findByResetPasswordToken(String token);


}
