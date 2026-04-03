package com.domain.cart.repository;

import com.domain.cart.entity.ShopCartEntity;

public interface ShopCartRepository {

    ShopCartEntity findByUserId(Long userId);

    void save(ShopCartEntity cartEntity);
}
