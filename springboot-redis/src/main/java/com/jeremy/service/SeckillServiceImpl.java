package com.jeremy.service;

import com.jeremy.dao.ProductDao;
import com.jeremy.exception.BusinessException;
import com.jeremy.model.Product;
import com.jeremy.util.KeyUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Author: laizc
 * @Date: Created in  2022-06-02
 * @desc:
 */
@Service
public class SeckillServiceImpl implements SeckillService{

	static Map<String,Integer> products;
	static Map<String,Integer> stock;
	static Map<String,String> orders;
	static
	{
		/**
		 * 模拟多个表，商品信息表，库存表，秒杀成功订单表
		 */
		products = new HashMap<>();
		stock = new HashMap<>();
		orders = new HashMap<>();
		products.put("123456", 100000);
		stock.put("123456", 100000);
	}

	@Override
	public void seckill(String productId) throws BusinessException {
		//1.查询该商品库存，为0则活动结束。
		int stockNum = stock.get(productId);
		if(stockNum == 0) {
			throw new BusinessException("活动结束");
		}else {
			//2.下单(模拟不同用户openid不同)
			orders.put(KeyUtil.genUniqueKey(),productId);
			//3.减库存
			stockNum =stockNum-1;
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			stock.put(productId,stockNum);
		}

	}

	@Override
	public String querySecKillProductInfo(String productId) {
		return this.queryMap(productId);
	}


	private String queryMap(String productId)
	{
		return "国庆活动，apple 手机"
				+ products.get(productId)
				+" 还剩：" + stock.get(productId)+" 份"
				+" 该商品成功下单用户数目："
				+  orders.size() +" 人" ;
	}
}
