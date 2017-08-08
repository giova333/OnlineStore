package com.gladunalexander.backend.service;

import com.gladunalexander.backend.persistence.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

/**
 * @author Alexander Gladun
 * Custom Security Service
 */

@Service
public class SecurityService {

    private static final Logger logger = LoggerFactory.getLogger(SecurityService.class);

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    /**
     * Finds logged in user and returns the name of this principal
     *
     * @return name of logged in user's principal or null if user is anonymous
     */
    public String findLoggedInUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            return authentication.getName();
        }
        return null;
    }

    /**
     * Makes autologin after user's successfully registration
     *
     * @param username user's username
     * @param password user's password
     */
    public void autoLogin(String username, String password){
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if (userDetails != null) {
            Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
    }

    /**
     * Returns authentication object
     * @return
     */
    public Authentication getAuthentication(){
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
