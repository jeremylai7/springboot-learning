package com.jeremy.service;

import com.jeremy.dao.ProductDao;
import com.jeremy.exception.BusinessException;
import com.jeremy.model.Product;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @Author: laizc
 * @Date: Created in  2022-06-02
 * @desc:
 */
@Service
public class SeckillServiceImpl implements SeckillService{

	@Resource
	private ProductDao productDao;

	@Override
	public String seckill(long id) throws BusinessException {
		Product product = productDao.selectByPrimaryKey(id);
		if (product == null) {
			throw new BusinessException("商品不存在");
		}
		int store = product.getStore();
		if (store > 0) {
			// 模拟下单

			// 扣减库存
			store = store - 1;
			product.setStore(store);
			productDao.updateByPrimaryKey(product);
			Product updateProduct = productDao.selectByPrimaryKey(id);
			return "抢购成功：" + updateProduct.getName() + "，剩余库存：" + updateProduct.getStore();
		} else {
			throw new BusinessException("库存不足");
		}

	}
}
