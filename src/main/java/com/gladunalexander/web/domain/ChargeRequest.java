package com.gladunalexander.web.domain;

import com.gladunalexander.backend.persistence.domain.Order;

/**
 * @author Alexander Gladun
 */
public class ChargeRequest {

    private String description;
    private Order order;
    private String stripeEmail;
    private String stripeToken;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getStripeEmail() {
        return stripeEmail;
    }

    public void setStripeEmail(String stripeEmail) {
        this.stripeEmail = stripeEmail;
    }

    public String getStripeToken() {
        return stripeToken;
    }

    public void setStripeToken(String stripeToken) {
        this.stripeToken = stripeToken;
    }

    @Override
    public String toString() {
        return "ChargeRequest{" +
                "description='" + description + '\'' +
                ", order=" + order +
                ", stripeEmail='" + stripeEmail + '\'' +
                ", stripeToken='" + stripeToken + '\'' +
                '}';
    }
}
