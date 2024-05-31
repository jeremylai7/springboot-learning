package com.jeremy.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * @author: laizc
 * @date: created in 2024/5/31
 * @desc:
 **/
@Data
@Document(indexName = "product_test")
public class ProductTest {
    @Id
    private String id;

    private String code;

    private String name;
    private String description;
    private double price;
}
