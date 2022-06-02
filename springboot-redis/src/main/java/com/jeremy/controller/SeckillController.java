package com.jeremy.controller;

import com.jeremy.exception.BusinessException;
import com.jeremy.service.SeckillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: laizc
 * @Date: Created in  2022-06-02
 * @desc: 秒杀接口
 */
@RestController
public class SeckillController {

	@Autowired
	private SeckillService seckillService;

	@GetMapping("/seckill")
	public String seckill(long id) throws BusinessException {
		String message = seckillService.seckill(id);
		return message;
	}

}
