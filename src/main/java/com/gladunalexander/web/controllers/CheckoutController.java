package com.gladunalexander.web.controllers;

import com.gladunalexander.backend.persistence.domain.Order;
import com.gladunalexander.backend.service.EmailService;
import com.gladunalexander.backend.service.OrderService;
import com.gladunalexander.backend.service.StripeService;
import com.gladunalexander.enums.OrderStatus;
import com.gladunalexander.exceptions.OrderErrorException;
import com.gladunalexander.web.domain.ChargeRequest;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

/**
 * @author Alexander Gladun
 */

@Controller
public class CheckoutController {

    private static final Logger LOG = LoggerFactory.getLogger(CheckoutController.class);

    @Autowired
    private OrderService orderService;

    @Autowired
    private StripeService stripeService;

    @Autowired
    private EmailService emailService;

    @Value("${stripe.public.key}")
    private String stripePublicKey;


    @RequestMapping(value = "/rest/order/add", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Order addOrder(@RequestBody Order order){
        LOG.info("This" + order);
        return orderService.prepaymentOrder(order);
    }

    @RequestMapping("/checkout/{orderNumber}")
    public String checkout(@PathVariable long orderNumber,
                           Model model, Authentication authentication){
        Order order = orderService.getByOrderNumber(orderNumber);
        if (order == null || !(order.getUser().getUsername().equals(authentication.getName()))
                || order.getOrderStatus() != OrderStatus.BEFORE_PAYMENT.getId()){
            throw new OrderErrorException("Order doesn't match");
        }
        model.addAttribute("order", order);
        model.addAttribute("stripePublicKey",stripePublicKey);
        return "checkout";
    }

    @RequestMapping(value = "/charge", method = RequestMethod.POST)
    public String charge(ChargeRequest chargeRequest, ModelMap model,
                         @RequestParam("orderNumber") long orderNumber) throws StripeException {
       Order order = orderService.getByOrderNumber(orderNumber);
       chargeRequest.setOrder(order);
       LOG.info("Charge request " + chargeRequest);
       Charge charge = stripeService.charge(chargeRequest);
       if (charge.getStatus().equals("succeeded")){
           order = orderService.compliteOrder(order);
           LOG.info("Order status updated" + order.getOrderStatus());
           emailService.sendOrderNotificationMail(order);
           model.addAttribute("success", "Thank you, check your email for reviewing order details");
       }else {
           model.addAttribute("failure", "In ordering opperation something was wrong, please retry...");
       }
       return MainController.PRODUCTS_PAGE;
    }
}
