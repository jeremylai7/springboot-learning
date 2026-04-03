package com.domain.cart.repository.impl;

import com.domain.cart.entity.CartProductEntity;
import com.domain.cart.entity.ShopCartEntity;
import com.domain.cart.entity.StoreProductEntity;
import com.domain.cart.repository.ShopCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: laizc
 * @date: 2026/4/3
 * @desc:
 */
@Service
public class ShopCartRepositoryImpl implements ShopCartRepository {

    //@Autowired
    //private RedisTemplate<String, String> redisTemplate;


    //  查询 redis 或者 mapper


    @Override
    public ShopCartEntity findByUserId(Long userId) {
        // 从 redis 或者 数据库获取数据，此处模拟查询的数据
        List<StoreProductEntity> storeProducts = new ArrayList<>();
        StoreProductEntity storeProductEntity = new StoreProductEntity();
        storeProductEntity.setStoreId(12L);
        List<CartProductEntity> products = new ArrayList<>();
        CartProductEntity cartProductEntity = new CartProductEntity();
        cartProductEntity.setSkuId(123L);
        cartProductEntity.setQuantity(2);
        products.add(cartProductEntity);
        storeProductEntity.setProducts(products);
        storeProducts.add(storeProductEntity);
        ShopCartEntity cartEntity = new ShopCartEntity();
        cartEntity.setUserId(userId);
        cartEntity.setStoreProducts(storeProducts);
        return null;
    }
}
