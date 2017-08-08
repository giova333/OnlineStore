package com.gladunalexander.backend.service;

import com.gladunalexander.backend.persistence.domain.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Alexander Gladun
 * Service layer for {@link com.gladunalexander.backend.persistence.repositories.UserRepository}
 */
public interface UserService {

    User saveOrUpdate(User user);

    User createUser(User user);

    User findByUsername(String username);

    User findByEmail(String email);

    User findById(long id);

    void delete(long id);

    List<User> findAll();

    void updateUserPassword(long id, String password);

    void updateUserStatus(long id, boolean status);

    String createPasswordResetUrl(HttpServletRequest request, long userId, String token);

    boolean duplicatesExists(User user);

}
