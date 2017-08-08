package com.gladunalexander.backend.service;

import com.gladunalexander.backend.persistence.domain.Product;
import com.gladunalexander.backend.persistence.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Alexander Gladun
 * Simple service layer for product management
 */

@Service
@Transactional(readOnly = true)
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAll(){
        return productRepository.findAll();
    }

    public Product getById(int id){
        return productRepository.getById(id);
    }

    @Transactional
    public Product addProduct(Product product){
        return productRepository.save(product);
    }

    @Transactional
    public void removeProduct(Product product){
        productRepository.delete(product);
    }
}
