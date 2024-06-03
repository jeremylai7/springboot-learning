package com.jeremy.repository;

import com.jeremy.model.ProductTest;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * @author: laizc
 * @date: created in 2024/5/31
 * @desc:
 **/
public interface ProductRepository extends ElasticsearchRepository<ProductTest,String> {

    List<ProductTest> findByName(String name);
}
