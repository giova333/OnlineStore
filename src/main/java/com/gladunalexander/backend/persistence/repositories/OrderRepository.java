package com.gladunalexander.backend.persistence.repositories;

import com.gladunalexander.backend.persistence.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author Alexander Gladun
 * Repository for order persisting
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

}
