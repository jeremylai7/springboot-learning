package com.jeremy.controller;

import com.jeremy.exception.BusinessException;
import com.jeremy.service.DistributedLockService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @author: laizc
 * @date: created in 2022-11-03
 * @desc: 分布式锁
 */
@RestController
@RequestMapping("/dl")
@Slf4j
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

    @GetMapping("/redission2")
    public void redission2(Long orderId) throws BusinessException {
        String lockKey = "demo_order_id:" + orderId;
        RLock lock = redissonClient.getLock(lockKey);
        try {
            Assert.isTrue(lock.tryLock(2, 10, TimeUnit.SECONDS), "正在处理中，请勿重复操作！");
            // 执行业务代码 xxxxxx
            //lockAllocation(param);

        } catch (Exception e) {
            log.error("操作失败, err:{}", e.getMessage(), e);
            throw new BusinessException("操作失败，失败原因:"+ e.getMessage());
        } finally {
            if (lock.isLocked() && lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }



}
