package com.domain.cart.service;

import com.domain.cart.entity.ShopCartEntity;

public interface ShopCartDomainService {

    ShopCartEntity findByUserId(Long userId);



}
