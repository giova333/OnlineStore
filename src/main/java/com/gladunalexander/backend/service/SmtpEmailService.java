package com.gladunalexander.backend.service;

import com.gladunalexander.backend.persistence.domain.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

/**
 * @author Alexander Gladun
 * Custom implementation for {@link EmailService}
 */
public class SmtpEmailService implements EmailService {

    private static final Logger LOG = LoggerFactory.getLogger(SmtpEmailService.class);

    @Autowired
    private MailSender mailSender;

    @Value("${spring.mail.username}")
    private String webMasterMail;

    /**
     * Sends email message generated from {@link SimpleMailMessage}
     * @param message The object containing the email content
     */
    @Override
    public void sendGenericEmailMessage(SimpleMailMessage message) {
        LOG.debug("Sending email for: {}", message);
        mailSender.send(message);
        LOG.info("Email sent.");
    }

    /**
     * Sends email after placing an order
     * @param order The object containing the oder information
     */
    @Override
    public void sendOrderNotificationMail(Order order){
        StringBuilder emailText = new StringBuilder("Your order Successfully complited. ")
                .append("Order Number: ")
                .append(order.getOrderNumber())
                .append("\r\n")
                .append("Total Price: ")
                .append(order.getTotalPrice())
                .append("$")
                .append("\r\n")
                .append("Product list: ");
        order.getCartItems().forEach(item -> emailText.append("\r\n")
                .append("Product Name: ")
                .append(item.getProduct().getProductName())
                .append(". Product quantity ")
                .append(item.getQuantity()));

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(order.getUser().getEmail());
        simpleMailMessage.setFrom(webMasterMail);
        simpleMailMessage.setSubject("[Spring Store]: Order number  " + order.getOrderNumber());
        simpleMailMessage.setText(emailText.toString());

        sendGenericEmailMessage(simpleMailMessage);
    }
}
