package com.gladunalexander.web.controllers.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gladunalexander.backend.persistence.domain.Product;
import com.gladunalexander.backend.service.ImageWorkerService;
import com.gladunalexander.backend.service.ProductService;
import com.gladunalexander.exceptions.ProductNotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author Alexander Gladun
 * Rest Service for products
 */

@RestController
@RequestMapping("/rest/products")
@Api(value="springstore", description="Operations for Products Management")
public class ProductRestController {

    private static final Logger LOG = LoggerFactory.getLogger(ProductRestController.class);

    @Autowired
    private ProductService productService;

    @Autowired
    private ImageWorkerService imageWorkerService;

    @Value("${resources.img.path}")
    private String rootDir;

    @GetMapping
    @ApiOperation(value = "View a list of products", response = List.class)
    public List<Product> findAll(){
        return productService.getAll();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Search a product with an ID",response = Product.class)
    public Product getById(@PathVariable int id){
        Product product = productService.getById(id);
        if (product == null){
            throw new ProductNotFoundException();
        }
        return product;
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Add a product")
    public Product add(@RequestBody Product product){
        return productService.addProduct(product);
    }

    @PostMapping("/addImage")
    @ApiOperation(value = "Add a product image")
    public void addProductImage(@RequestParam(value = "file") MultipartFile file) throws JsonProcessingException {
        if (file != null && !file.isEmpty()){
           imageWorkerService.saveImage(file);
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete a product")
    public void removeProduct(@PathVariable int id){
        Product product = productService.getById(id);
        if (product == null){
            throw new ProductNotFoundException();
        }
        productService.removeProduct(product);
        imageWorkerService.removeImage(product.getProductImage());
    }
}
