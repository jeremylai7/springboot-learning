package com.domain.cart.service.impl;

import com.domain.cart.entity.ShopCartEntity;
import com.domain.cart.repository.ShopCartRepository;
import com.domain.cart.service.ShopCartDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: laizc
 * @date: 2026/4/3
 * @desc:
 */
@Service
public class ShopCartDomainServiceImpl implements ShopCartDomainService {

    @Autowired
    private ShopCartRepository shopCartRepository;

    @Override
    public ShopCartEntity findByUserId(Long userId) {
        return shopCartRepository.findByUserId(userId);
    }
}
