package com.jeremy.service;

import com.jeremy.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: laizc
 * @date: created in 2022-11-03
 * @desc: 分布式锁
 */
@Service
public class DistributedLockServiceImpl implements DistributedLockService{

    /**
     * 超时时间 10s
     */
    private static final int TIME_OUT = 10 * 1000;

    @Autowired
    private RedisLock redisLock;


    @Override
    public void order() throws BusinessException {
        // 加锁
        String productId = "001";
        long time = System.currentTimeMillis() + TIME_OUT;
        if (redisLock.lock(productId,String.valueOf(time))) {
            throw new BusinessException("66","抢购人数太多了");
        }

        // 解锁
        redisLock.unlock(productId,String.valueOf(time));


    }
}
