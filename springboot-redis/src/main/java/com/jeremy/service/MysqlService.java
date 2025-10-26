package com.jeremy.service;

import com.jeremy.exception.BusinessException;
import com.jeremy.model.Order;

/**
 * @author: laizc
 * @date: created in 2023/3/28
 * @desc:
 */
public interface MysqlService {

    void addOrder(Order order) throws Exception;

    void seckill(Long productId) throws BusinessException;
}
