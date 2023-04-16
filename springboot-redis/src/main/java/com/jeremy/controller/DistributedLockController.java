package com.jeremy.controller;

import com.jeremy.exception.BusinessException;
import com.jeremy.service.DistributedLockService;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: laizc
 * @date: created in 2022-11-03
 * @desc: 分布式锁
 */
@RestController
@RequestMapping("/dl")
public class DistributedLockController {

    @Autowired
    private DistributedLockService distributedLockService;

    @Autowired
    private RedissonClient redissonClient;

    /**
     * 分布式锁实现订单秒杀
     * @return
     */
    @PostMapping("/order")
    public String order() throws BusinessException {
        distributedLockService.order();
        return "ok";
    }

    @GetMapping("/redission")
    public String redission() {
        // 获取锁
        RLock rLock = redissonClient.getLock("lock");
        // 加锁
        rLock.lock();

        // 执行业务数据

        // 解锁
        rLock.unlock();
        return "ok";

    }



}
