package com.jeremy.service;

import com.jeremy.redis.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: laizc
 * @date: created in 2024/5/4
 * @desc:
 **/
@Service
public class DeliveryServiceImpl implements DeliveryService {

    @Autowired
    private RedisService redisService;

    @Override
    public void test() {
        redisService.set("a","acc");
    }
}
