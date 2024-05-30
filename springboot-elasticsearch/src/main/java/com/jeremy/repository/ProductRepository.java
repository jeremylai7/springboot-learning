package com.jeremy.repository;

import com.jeremy.model.Product;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;

import java.util.List;

/**
 * @author: laizc
 * @date: created in 2024/5/31
 * @desc:
 **/
public interface ProductRepository extends ElasticsearchCrudRepository<Product,String> {

    List<Product> findByName(String name);
}
