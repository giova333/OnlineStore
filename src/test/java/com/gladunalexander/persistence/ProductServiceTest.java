package com.gladunalexander.persistence;

import com.gladunalexander.backend.persistence.domain.Product;
import com.gladunalexander.backend.service.ProductService;
import com.gladunalexander.enums.ProductTypes;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Alexander Gladun
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Test
    public void getAllTest(){
        List<Product> products = productService.getAll();
        Assert.assertTrue(!CollectionUtils.isEmpty(products));
    }

    @Test
    public void getByIdTest(){
        Product product = productService.getById(1);
        Assert.assertEquals(product.getId(), 1);
    }

    @Test
    public void saveProduct(){
        Product product = productService.addProduct(createProduct());
        Assert.assertNotNull(product);
        Assert.assertNotNull(product.getId());
    }

    @Test
    public void removeTest(){
        Product product = productService.addProduct(createProduct());
        productService.removeProduct(product);
        Assert.assertNull(productService.getById(product.getId()));
    }

    private Product createProduct(){
        Product product = new Product();
        product.setProductName("iphone 3g");
        product.setProductType(ProductTypes.PHONE);
        product.setProductDescription("Description");
        product.setProductPrice(new BigDecimal(231.2));
        product.setProductManufacturer("China");
        product.setProductImage("img");
        product.setUnitInStock(1000);

        return product;
    }
}
