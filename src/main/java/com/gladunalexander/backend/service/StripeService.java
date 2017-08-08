package com.gladunalexander.backend.service;

import com.gladunalexander.backend.persistence.domain.Order;
import com.gladunalexander.exceptions.OrderErrorException;
import com.gladunalexander.web.domain.ChargeRequest;
import com.stripe.Stripe;
import com.stripe.exception.*;
import com.stripe.model.Charge;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Alexander Gladun
 * Stripe payment service
 */

@Service
public class StripeService {

    private static final String DEFAULT_CURRENCY = "USD";

    @Value("${stripe.secret.key}")
    private String privateKey;


    @PostConstruct
    public void init(){
        Stripe.apiKey = privateKey;
    }

    /**
     * Makes payment, from {@link ChargeRequest}
     * @param chargeRequest object witch stores all purchasing info
     * @return Charge object
     * @throws CardException
     * @throws APIException
     * @throws AuthenticationException
     * @throws InvalidRequestException
     * @throws APIConnectionException
     */
    public Charge charge(ChargeRequest chargeRequest) throws CardException, APIException, AuthenticationException,
            InvalidRequestException, APIConnectionException {
        Order order = chargeRequest.getOrder();

        if (order == null || order.getTotalPrice() == null){
            throw new OrderErrorException("Wrong order parameters");
        }

        Map<String, Object> chargeParams = new HashMap<>();
        chargeParams.put("amount", order.getTotalPrice().intValue() * 100);
        chargeParams.put("currency", DEFAULT_CURRENCY );
        chargeParams.put("description", "Order Payment");
        chargeParams.put("source", chargeRequest.getStripeToken());
        return Charge.create(chargeParams);
    }
}
