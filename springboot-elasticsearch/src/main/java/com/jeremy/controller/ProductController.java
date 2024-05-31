package com.jeremy.controller;

import com.jeremy.model.ProductTest;
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
    public ProductTest createProduct(@RequestBody ProductTest product) {
        return productService.saveProduct(product);
    }

    @GetMapping("/{id}")
    public ProductTest getProductById(@PathVariable String id) {
        return productService.findById(id);
    }

    @GetMapping("/name/{name}")
    public List<ProductTest> getProductsByName(@PathVariable String name) {
        return productService.findByName(name);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable String id) {
        productService.deleteProduct(id);
    }
}
