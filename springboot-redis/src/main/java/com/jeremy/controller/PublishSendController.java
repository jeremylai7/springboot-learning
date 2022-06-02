package com.jeremy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: laizc
 * @Date: Created in  2022-04-21
 * @desc: 发布订阅模式
 */
@RestController
public class PublishSendController {

	@Autowired
	private StringRedisTemplate redisTemplate;

	@GetMapping("/publish-send")
	public String publishSend(String message) {
		redisTemplate.convertAndSend("runoobChat",message);
		return "ok";
	}

	@GetMapping("/publish-send2")
	public String publishSend2(String message) {
		redisTemplate.convertAndSend("runoobChat2",message);
		return "ok";
	}

}
