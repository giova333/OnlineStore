package com.gladunalexander.backend.persistence.repositories;

import com.gladunalexander.backend.persistence.domain.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * @author Alexander Gladun
 * Repository for password reset tokens persisting
 */

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {

    PasswordResetToken findByToken(String token);

    @Query("select ptr from PasswordResetToken ptr where ptr.user.id =?1")
    Set<PasswordResetToken> findByUserId(long id);
}
