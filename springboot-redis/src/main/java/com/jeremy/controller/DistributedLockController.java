package com.jeremy.controller;

import com.jeremy.exception.BusinessException;
import com.jeremy.service.DistributedLockService;
import org.springframework.beans.factory.annotation.Autowired;
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

    /**
     * 分布式锁实现订单秒杀
     * @return
     */
    @PostMapping("/order")
    public String order() throws BusinessException {
        distributedLockService.order();
        return "ok";
    }



}
