package com.gladunalexander.web.controllers;

import com.gladunalexander.backend.persistence.domain.PasswordResetToken;
import com.gladunalexander.backend.persistence.domain.User;
import com.gladunalexander.backend.service.EmailService;
import com.gladunalexander.backend.service.PasswordResetTokenService;
import com.gladunalexander.backend.service.SecurityService;
import com.gladunalexander.backend.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.time.Clock;
import java.time.LocalDateTime;

/**
 * @author Alexander Gladun
 * Controller for forgot password
 */

@Controller
public class ForgotMyPasswordController {

    private static final Logger LOG = LoggerFactory.getLogger(ForgotMyPasswordController.class);

    private static final String FORGOT_MY_PASSWORD_ERROR = "tokenError";

    private static final String PASSWORD_RESET_VALID = "passwordReset";

    private static final String CHANGE_PASSWORD_VIEW = "changePassword";

    public static final String CHANGE_PASSWORD_PATH = "/changeuserpassword";

    @Autowired
    private EmailService emailService;

    @Autowired
    private SecurityService securityService;

    @Value("${spring.mail.username}")
    private String webMasterMail;

    @Autowired
    private PasswordResetTokenService passwordResetTokenService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/forgotmypassword", method = RequestMethod.POST)
    public String forgotPasswordPost(@RequestParam("email") String email,
                                     HttpServletRequest request,
                                     Model model){

        PasswordResetToken passwordResetToken = passwordResetTokenService.createPasswordResetTokenForEmail(email);

        if (passwordResetToken == null){
            LOG.warn("Couldn't find a password reset token for email {}", email);
            model.addAttribute("wrong", "This email is not registered");
        }else {

            User user = passwordResetToken.getUser();
            String token = passwordResetToken.getToken();

            String resetPasswordUrl = userService.createPasswordResetUrl(request, user.getId(), token);
            LOG.debug("Reset Password URL {}", resetPasswordUrl);

            String emailText = "Please click on the link below to reset your password:";

            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(user.getEmail());
            mailMessage.setSubject("[Spring Store]: How to Reset Your Password");
            mailMessage.setText(emailText + "\r\n" + resetPasswordUrl);
            mailMessage.setFrom(webMasterMail);

            emailService.sendGenericEmailMessage(mailMessage);
            model.addAttribute("message", "Mail has been sent");

        }
        return LoginController.LOGIN_VIEW_NAME;
    }

    @RequestMapping(value = CHANGE_PASSWORD_PATH, method = RequestMethod.GET)
    public String changePasswordGet(@RequestParam("id") long id,
                                    @RequestParam("token") String token,
                                    Model model){
        if (StringUtils.isEmpty(token) || id == 0){
            LOG.error("Invalid user id {}  or token value {}", id, token);
            model.addAttribute(FORGOT_MY_PASSWORD_ERROR, "Invalid user id or token value");
            model.addAttribute(PASSWORD_RESET_VALID, "false");
            return CHANGE_PASSWORD_VIEW;
        }

        PasswordResetToken passwordResetToken = passwordResetTokenService.findByToken(token);

        if (passwordResetToken == null){
            LOG.warn("A token couldn't be found with value {}", token);
            model.addAttribute(FORGOT_MY_PASSWORD_ERROR, "Token not found");
            model.addAttribute(PASSWORD_RESET_VALID, "false");
            return CHANGE_PASSWORD_VIEW;
        }

        User user = passwordResetToken.getUser();

        if (user.getId() != id){
            LOG.error("The user id {} passed as parameter does not match the user id {} associated with the token {}",
                    id, user.getId(), token);
            model.addAttribute(FORGOT_MY_PASSWORD_ERROR, "Invalid user id");
            model.addAttribute(PASSWORD_RESET_VALID, "false");
            return CHANGE_PASSWORD_VIEW;
        }

        if (LocalDateTime.now(Clock.systemUTC()).isAfter(passwordResetToken.getExpiryDate())) {
            LOG.error("The token {} has expired", token);
            model.addAttribute(FORGOT_MY_PASSWORD_ERROR, "Token expired, please repeat procedure");
            model.addAttribute(PASSWORD_RESET_VALID, "false");
            return CHANGE_PASSWORD_VIEW;
        }

        model.addAttribute("principalId", user.getId());

        securityService.autoLogin(user.getUsername(), null);

        return CHANGE_PASSWORD_VIEW;
    }

    @RequestMapping(value = CHANGE_PASSWORD_PATH, method = RequestMethod.POST)
    public String changePasswordPost(@RequestParam("principal_Id") long userId,
                                     @RequestParam("password") String password,
                                     Model model){

        Authentication authentication = securityService.getAuthentication();

        if (authentication == null){
            LOG.error("An unauthenticated user tried to invoke the reset password POST method");
            model.addAttribute(FORGOT_MY_PASSWORD_ERROR, "You are not authorized to perform this request.");
            model.addAttribute(PASSWORD_RESET_VALID, "false");
            return CHANGE_PASSWORD_VIEW;
        }

        User user = userService.findByUsername(authentication.getName());

        if (user.getId() != userId){
            LOG.error("Security breach! User {} is trying to make a password reset request on behalf of {}",
                    user.getId(), userId);
            model.addAttribute(FORGOT_MY_PASSWORD_ERROR, "You are not authorized to perform this request.");
            model.addAttribute(PASSWORD_RESET_VALID, "false");
            return CHANGE_PASSWORD_VIEW;
        }
        userService.updateUserPassword(userId, password);
        LOG.info("Password successfully updated for user {}", user.getUsername());
        return "redirect:/login";
    }
}
