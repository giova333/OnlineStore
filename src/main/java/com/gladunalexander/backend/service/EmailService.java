package com.gladunalexander.backend.service;

import com.gladunalexander.backend.persistence.domain.Order;
import org.springframework.mail.SimpleMailMessage;

/**
 * @author Alexander Gladun
 * Contract for Email Service
 */
public interface EmailService {

    /**
     * Sends an email with the content of the Simple Mail Message object
     * @param message The object containing the email content
     */
     void sendGenericEmailMessage(SimpleMailMessage message);

    /**
     * Sends an email notification after successful order
     * completion
     * @param order The object containing the oder information
     */
    void sendOrderNotificationMail(Order order);
}
