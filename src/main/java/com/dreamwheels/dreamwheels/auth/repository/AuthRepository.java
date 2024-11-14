package com.dreamwheels.dreamwheels.auth.repository;

import com.dreamwheels.dreamwheels.users.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthRepository extends JpaRepository<User, String> {
   Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByUserName(String userName);

}
