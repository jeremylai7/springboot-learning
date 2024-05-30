package com.jeremy.service;

import com.jeremy.model.Product;
import com.jeremy.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: laizc
 * @date: created in 2024/5/31
 * @desc:
 **/
@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public Product findById(String id) {
        return productRepository.findById(id).orElse(null);
    }

    public List<Product> findByName(String name) {
        return productRepository.findByName(name);
    }

    public void deleteProduct(String id) {
        productRepository.deleteById(id);
    }
}
