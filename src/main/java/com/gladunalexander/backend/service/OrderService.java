package com.gladunalexander.backend.service;

import com.gladunalexander.backend.persistence.domain.Order;
import com.gladunalexander.backend.persistence.repositories.OrderRepository;
import com.gladunalexander.enums.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * @author Alexander Gladun
 */

@Service
@Transactional(readOnly = true)
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserService userService;

    @Transactional
    private Order saveOrder(Order order){
        return orderRepository.save(order);
    }

    /**
     * Saves order and all associated cart items.
     * This methods triggered before payment compliance
     * @param order
     * @return Persisted order
     */
    @Transactional
    public Order prepaymentOrder(Order order){
        order.setUser(userService.findByUsername(securityService.findLoggedInUser()));
        order.setOrderStatus(OrderStatus.BEFORE_PAYMENT);
        order.getCartItems().forEach(cartItem -> cartItem.setOrder(order));
        return saveOrder(order);
    }

    /**
     * Retrieve order by number
     * @param orderNumber
     * @return retrieved order
     */
    public Order getByOrderNumber(long orderNumber){
       return orderRepository.getOne(orderNumber);
    }

    /**
     * Updates order status and date. This methods triggered after
     * payment successful compliance
     * @param order
     * @return updated order
     */
    @Transactional
    public Order compliteOrder(Order order){
        order.setOrderStatus(OrderStatus.COMPLITED);
        order.setOrderDate(LocalDateTime.now());
        return saveOrder(order);
    }
}
