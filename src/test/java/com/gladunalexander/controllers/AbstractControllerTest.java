package com.gladunalexander.controllers;

import com.gladunalexander.backend.persistence.domain.User;
import com.gladunalexander.backend.service.UserService;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.Filter;

/**
 * Created Alexander Gladun
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class AbstractControllerTest extends AbstractTransactionalJUnit4SpringContextTests {

    private static final String ADMIN_USERNAME = "admin";

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private Filter springSecurityFilterChain;

    @Autowired
    private UserDetailsService userDetailsService;

    protected MockMvc mockMvc;

    @Before
    public void setup(){
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .addFilter(springSecurityFilterChain)
                .build();
    }

    public UserDetails admin(){
        return userDetailsService.loadUserByUsername(ADMIN_USERNAME);
    }
}
