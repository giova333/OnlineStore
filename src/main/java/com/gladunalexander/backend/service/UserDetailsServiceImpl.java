package com.gladunalexander.backend.service;

import com.gladunalexander.backend.persistence.domain.CustomUserDetails;
import com.gladunalexander.backend.persistence.domain.User;
import com.gladunalexander.backend.persistence.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author Alexander Gladun
 * Implementation for {@link UserDetailsService}
 */

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final Logger LOG = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUsername(username);
        if (user == null){
            throw new UsernameNotFoundException("Username " + username + " not found");
        }
        LOG.info("User loaded "  + username);
        return new CustomUserDetails(user);
    }
}
