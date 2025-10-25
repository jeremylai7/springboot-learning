package com.jeremy.controller;

import com.jeremy.exception.BusinessException;
import com.jeremy.service.SeckillService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: laizc
 * @Date: Created in  2022-06-02
 * @desc: 秒杀接口
 */
@RestController
@RequestMapping("/skill")
@Slf4j
public class SeckillController {

	@Autowired
	private SeckillService seckillService;

	/**
	 * 秒杀下单
	 * ab 压测工具 ab -n 100 -c 10 http://localhost:8080/skill/order/123456
	 * 效果:下单 100 库存小于 80
	 *
	 * @return
	 * @throws BusinessException
	 */
	@GetMapping("/order/{productId}")
	public String skill(@PathVariable String productId) throws BusinessException {
		log.info("@skill request, productId:" + productId);
		seckillService.seckill(productId);
		return seckillService.querySecKillProductInfo(productId);
	}

	/**
	 * 查询秒杀活动特价商品的信息
	 * @param productId
	 * @return
	 */
	@GetMapping("/query/{productId}")
	public String query(@PathVariable String productId) {
		return seckillService.querySecKillProductInfo(productId);
	}

}
