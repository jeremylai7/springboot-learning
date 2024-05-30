package com.jeremy.controller;

import com.jeremy.model.Product;
import com.jeremy.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: laizc
 * @date: created in 2024/5/31
 * @desc:
 **/
@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productService.saveProduct(product);
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable String id) {
        return productService.findById(id);
    }

    @GetMapping("/name/{name}")
    public List<Product> getProductsByName(@PathVariable String name) {
        return productService.findByName(name);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable String id) {
        productService.deleteProduct(id);
    }
}
