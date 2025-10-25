package com.jeremy.service;

import com.jeremy.exception.BusinessException;

/**
 * @Author: laizc
 * @Date: Created in  2022-06-02
 * @desc:
 */
public interface SeckillService {

	void seckill(String id) throws BusinessException;

	String querySecKillProductInfo(String productId);
}
