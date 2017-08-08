package com.gladunalexander.controllers;

import org.junit.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

/**
 * @author Alexander Gladun
 */
public class MainControllerTest extends AbstractControllerTest {

    @Test
    public void testWelcomePage() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("welcome"));
    }

    @Test
    public void testProductsPage() throws Exception {
        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(view().name("products"));
    }

    @Test
    public void testCartPage() throws Exception {
        mockMvc.perform(get("/cart"))
                .andExpect(status().isOk())
                .andExpect(view().name("cart"));
    }

    @Test
    public void testUserPageSuccess() throws Exception {
        mockMvc.perform(get("/users").with(user(admin())))
                .andExpect(status().isOk())
                .andExpect(view().name("users"));
    }

    @Test
    public void testUserPageFailure() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }
}
