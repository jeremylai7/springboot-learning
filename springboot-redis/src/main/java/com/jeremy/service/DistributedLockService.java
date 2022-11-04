package com.jeremy.service;

import com.jeremy.exception.BusinessException;

/**
 * @author: laizc
 * @date: created in 2022-11-03
 * @desc:
 */
public interface DistributedLockService {
    /**
     * 秒杀下单
     */
    void order() throws BusinessException;
}
