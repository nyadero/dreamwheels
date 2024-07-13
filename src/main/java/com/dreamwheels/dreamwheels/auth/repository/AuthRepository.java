package com.dreamwheels.dreamwheels.auth.repository;

import com.dreamwheels.dreamwheels.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthRepository extends JpaRepository<User, String> {
    User findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByUserName(String userName);
}
