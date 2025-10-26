package com.jeremy.dao;

import com.jeremy.model.Product;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

/**
 * @Author: laizc
 * @Date: Created in  2022-06-02
 * @desc:
 */
public interface ProductDao extends Mapper<Product>{

    Product selectByIdForUpdate(long id);

    void updateStoreById(@Param("stockNum") long stockNum, @Param("productId") Long productId);
}
