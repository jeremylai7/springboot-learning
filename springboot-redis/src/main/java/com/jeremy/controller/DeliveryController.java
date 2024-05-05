package com.jeremy.controller;

import com.jeremy.redis.RedisService;
import com.jeremy.service.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: laizc
 * @date: created in 2024/5/4
 * @desc: 传送
 **/
@RestController
@RequestMapping("/delivery")
public class DeliveryController {

    @Autowired
    private DeliveryService deliveryService;

    @Autowired
    private RedisService redisService;


    @GetMapping
    public String test() {
        deliveryService.test();
        return redisService.get("a");
    }


}
