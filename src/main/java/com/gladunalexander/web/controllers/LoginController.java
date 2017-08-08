package com.gladunalexander.web.controllers;

import com.gladunalexander.backend.persistence.domain.User;
import com.gladunalexander.backend.service.EmailService;
import com.gladunalexander.backend.service.SecurityService;
import com.gladunalexander.backend.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Alexander Gladun
 */
@Controller
public class LoginController {

    public static final String LOGIN_VIEW_NAME = "login";

    private static final String SIGN_UP_VIEW = "signup";

    private static final Logger LOG = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private SecurityService securityService;

    @Value("${default.to.address}")
    private String webMasterEmail;

    @RequestMapping("/login")
    public String login(Model model, String error, String logout, String denied){
        if (error != null) {
            model.addAttribute("error", "Username or password is incorrect.");
        }

        if (logout != null) {
            model.addAttribute("message", "Logged out successfully.");
        }

        if (denied != null) {
            model.addAttribute("error", "Access denied");
        }
        return LOGIN_VIEW_NAME;
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signupGet(Model model){
        model.addAttribute("user", new User());
        return SIGN_UP_VIEW;
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String signupPost(@ModelAttribute("user") User user, Model model){
        if (checkForDuplicates(user, model)){
            return SIGN_UP_VIEW;
        }
        userService.createUser(user);
        LOG.info("New user created: " + user);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("[Spring Store]: Account has been created");
        mailMessage.setText(user.getFirstName() +" welcome to Spring Store!");
        mailMessage.setFrom(webMasterEmail);

        emailService.sendGenericEmailMessage(mailMessage);
        LOG.info("Message to user: " + user + " has been sent " + mailMessage);

        securityService.autoLogin(user.getUsername(), user.getPassword());
        LOG.info("User has been authenticated " + user);

        return "redirect:/";
    }

    private boolean checkForDuplicates(User user, Model model){
        if (userService.findByUsername(user.getUsername()) != null){
            model.addAttribute("duplicateUsername", "User with this username already exists");
            return true;
        }

        if (userService.findByEmail(user.getEmail()) != null){
            model.addAttribute("duplicateEmail", "User with this email already exists");
            return true;
        }
        return false;
    }
}
