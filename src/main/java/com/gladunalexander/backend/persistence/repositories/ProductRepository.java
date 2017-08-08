package com.gladunalexander.backend.persistence.repositories;

import com.gladunalexander.backend.persistence.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Alexander Gladun
 * Repository for product persisting
 */

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    Product getById(int id);
}
