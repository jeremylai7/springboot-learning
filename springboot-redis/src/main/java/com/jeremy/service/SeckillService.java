package com.jeremy.service;

import com.jeremy.exception.BusinessException;

/**
 * @Author: laizc
 * @Date: Created in  2022-06-02
 * @desc:
 */
public interface SeckillService {

	String seckill(long id) throws BusinessException;

}
