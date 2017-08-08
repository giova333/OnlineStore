package com.gladunalexander.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Alexander Gladun
 */

@Controller
public class MainController {

    public static final String WELCOME_PAGE = "welcome";

    public static final String PRODUCTS_PAGE = "products";

    private static final String USERS_PAGE = "users";

    private static final String CART_PAGE = "cart";

    @RequestMapping("/")
    public String welcome(){
        return WELCOME_PAGE;
    }

    @RequestMapping("/products")
    public String showProducts(){
        return PRODUCTS_PAGE;
    }

    @RequestMapping("/users")
    public String getUsersPage(){
        return USERS_PAGE;
    }

    @RequestMapping("/cart")
    public String showCart(){
        return CART_PAGE;
    }
}
