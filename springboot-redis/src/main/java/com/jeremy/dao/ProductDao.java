package com.jeremy.dao;

import com.jeremy.model.Product;
import tk.mybatis.mapper.common.Mapper;

/**
 * @Author: laizc
 * @Date: Created in  2022-06-02
 * @desc:
 */
public interface ProductDao extends Mapper<Product>{

    Product selectById(long id);
}
