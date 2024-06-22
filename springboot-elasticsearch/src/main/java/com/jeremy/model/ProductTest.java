package com.jeremy.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @author: laizc
 * @date: created in 2024/5/31
 * @desc:
 **/
@Data
@Document(indexName = "product_test")
public class ProductTest {

    public ProductTest(){}

    public ProductTest(String id,String code,String name,double price) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.price = price;
    }

    @Id
    private String id;

    @Field(type = FieldType.Keyword)
    private String code;

    private String name;
    private String description;
    private double price;

    private Long productCount;

    private double sumPrice;


}
