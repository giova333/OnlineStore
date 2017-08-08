package com.gladunalexander.controllers;

import org.junit.Test;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Created Alexander Gladun
 */
public class LoginControllerTest extends AbstractControllerTest {

    @Test
    public void testLogin() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    public void testSignupGet() throws Exception{
        mockMvc.perform(get("/signup"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("user"))
                .andExpect(view().name("signup"));
    }

    @Test
    public void testSignupPost() throws Exception{
        mockMvc.perform(post("/signup")
                .param("firstName", "Daniel")
                .param("lastName", "Pearce")
                .param("username", "daniel333")
                .param("password", "daniel333")
                .param("confirmPassword", "daniel333")
                .param("email", "daniel333@gmail.com")
                .with(csrf())
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"))
                .andExpect(view().name("redirect:/"));

    }
}
