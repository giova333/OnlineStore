package com.gladunalexander.backend.service;

import com.gladunalexander.backend.persistence.domain.PasswordResetToken;
import com.gladunalexander.backend.persistence.domain.Role;
import com.gladunalexander.backend.persistence.domain.User;
import com.gladunalexander.backend.persistence.repositories.PasswordResetTokenRepository;
import com.gladunalexander.backend.persistence.repositories.UserRepository;
import com.gladunalexander.enums.RolesEnum;
import com.gladunalexander.web.controllers.ForgotMyPasswordController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Alexander Gladun
 * Custom implememntation for {@link UserService}
 */

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public User saveOrUpdate(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        setBaseRole(user);
        userRepository.save(user);
        return user;
    }

    @Override
    @Transactional
    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(true);
        setBaseRole(user);
        userRepository.save(user);
        return user;
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User findById(long id) {
        return userRepository.findById(id);
    }

    @Override
    @Transactional
    public void delete(long id) {
        userRepository.delete(id);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public void updateUserPassword(long id, String password) {
        password = passwordEncoder.encode(password);
        userRepository.updateUserPassword(id, password);
        LOG.debug("Password updated successfully for user id {} ", id);

        Set<PasswordResetToken> passwordResetTokens = passwordResetTokenRepository.findByUserId(id);
        if (!CollectionUtils.isEmpty(passwordResetTokens)){
            passwordResetTokenRepository.delete(passwordResetTokens);
        }
    }

    @Override
    @Transactional
    public void updateUserStatus(long id, boolean status) {
        userRepository.updateUserStatus(id, status);
    }

    /**
     * Builds and returns the URL to reset the user password.
     * @param request The Http Servlet Request
     * @param userId The user id
     * @param token The token
     * @return the URL to reset the user password.
     */
    public String createPasswordResetUrl(HttpServletRequest request, long userId, String token) {
        String passwordResetUrl =
                request.getScheme() +
                        "://" +
                        request.getServerName() +
                        ":" +
                        request.getServerPort() +
                        request.getContextPath() +
                        ForgotMyPasswordController.CHANGE_PASSWORD_PATH +
                        "?id=" +
                        userId +
                        "&token=" +
                        token;

        return passwordResetUrl;
    }

    /**
     * Checks if username or email already exists
     * @param user
     * @return
     */
    public boolean duplicatesExists(User user) {
        User old = findByUsername(user.getUsername());
        if (old != null && user.getId() != old.getId()) {
            return true;
        }

        old = findByEmail(user.getEmail());
        if (old != null && user.getId() != old.getId()){
            return true;
        }
        return false;
    }

    /**
     * Setting base USER role to current User
     * @param user
     */
    private void setBaseRole(User user){
        Set<Role> roles = new HashSet<>();
        roles.add(new Role(RolesEnum.USER));
        user.setRoles(roles);
    }
}
