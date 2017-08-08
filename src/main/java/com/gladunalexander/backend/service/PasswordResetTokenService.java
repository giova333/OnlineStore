package com.gladunalexander.backend.service;
import com.gladunalexander.backend.persistence.domain.PasswordResetToken;
import com.gladunalexander.backend.persistence.domain.User;
import com.gladunalexander.backend.persistence.repositories.PasswordResetTokenRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.UUID;


/**
 * @author Alexander Gladun
 */

@Service
@Transactional(readOnly = true)
public class PasswordResetTokenService {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Value("${token.expiration.length.minutes}")
    private int tokenExpirationInMinutes;

    private static final Logger LOG = LoggerFactory.getLogger(PasswordResetTokenService.class);

    /**
     * Creates a new Password Reset Token for the user identified by the given email.
     * @param email The email uniquely identifying the user
     * @return a new Password Reset Token for the user identified by the given email or null if none was found
     */

    @Transactional
    public PasswordResetToken createPasswordResetTokenForEmail(String email){
        PasswordResetToken passwordResetToken = null;
        User user = userService.findByEmail(email);

        if (user != null){
            String token = UUID.randomUUID().toString();
            LocalDateTime now = LocalDateTime.now(Clock.systemUTC());
            passwordResetToken = new PasswordResetToken(token, user, now, tokenExpirationInMinutes);
            passwordResetTokenRepository.save(passwordResetToken);

            LOG.debug("Successfully created token {}  for user {}", token, user.getUsername());
        } else {
            LOG.warn("We couldn't find a user for the given email {}", email);
        }
        return passwordResetToken;
    }

    /**
     * Retrieves a Password Reset Token for the given token id.
     * @param token The token to be returned
     * @return A Password Reset Token if one was found or null if none was found.
     */

    public PasswordResetToken findByToken(String token){
        return passwordResetTokenRepository.findByToken(token);
    }
}
