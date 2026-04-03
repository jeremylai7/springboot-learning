package com.application.service.impl;

import com.application.dto.AddCartCommand;
import com.application.dto.CartProductDTO;
import com.application.service.ShopCartApplicationService;
import com.domain.cart.entity.CartProductEntity;
import com.domain.cart.entity.ShopCartEntity;
import com.domain.cart.entity.StoreProductEntity;
import com.domain.cart.repository.ShopCartRepository;
import com.domain.cart.service.ShopCartDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: laizc
 * @date: 2026/4/3
 * @desc:
 */
@Service
public class ShopCartServiceApplicationImpl implements ShopCartApplicationService {

    @Autowired
    private ShopCartDomainService shopCartDomainService;

    @Autowired
    private ShopCartRepository shopCartRepository;

    @Override
    public void addProduct(AddCartCommand command) {
        // 检验商品是否为null
        command.getStoreProducts().forEach(storeProduct -> {
            storeProduct.getProducts().forEach(cartProduct -> {
                if (cartProduct.getSkuId() == null) {
                    throw new IllegalArgumentException("商品id不能为空");
                }
                if (cartProduct.getQuantity() == null || cartProduct.getQuantity() <= 0) {
                    throw new IllegalArgumentException("商品数量必须大于0");
                }
            });
        });
        // 查询店铺
        // 查询商品
        // 并且校验，
        // 此处省略......
        Long userId = command.getUserId();
        // 查询商品 模拟查询的数据
        ShopCartEntity cartEntity = shopCartDomainService.findByUserId(userId);
        command.getStoreProducts().forEach(storeProduct -> {
            Long storeId = storeProduct.getStoreId();
            List<CartProductDTO> products = storeProduct.getProducts();
            List<CartProductEntity> productEntities = new ArrayList<>();
            products.stream().forEach(cartProduct -> {
                Long skuId = cartProduct.getSkuId();
                Integer quantity = cartProduct.getQuantity();
                CartProductEntity productEntity = new CartProductEntity();
                productEntity.setSkuId(skuId);
                productEntity.setQuantity(quantity);
                productEntities.add(productEntity);
            });
            StoreProductEntity storeProductEntity = new StoreProductEntity();
            storeProductEntity.setStoreId(storeId);
            storeProductEntity.setProducts(productEntities);
            // 添加到购物车
            cartEntity.addStoreProduct(storeProductEntity);
        });
        shopCartRepository.save(cartEntity);
    }


}
