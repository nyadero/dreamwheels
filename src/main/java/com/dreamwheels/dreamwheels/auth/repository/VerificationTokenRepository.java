package com.dreamwheels.dreamwheels.auth.repository;

import com.dreamwheels.dreamwheels.auth.entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, String> {
    Optional<VerificationToken> findByToken(String token);
}
