package com.jeremy.controller;

import com.alibaba.fastjson.JSON;
import com.jeremy.User;
import com.jeremy.redis.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: laizc
 * @Date: Created in  2021-11-24
 * @desc:
 */
@RestController
public class TestController {

	@Autowired
	private RedisService redisService;

	@PostMapping("/set")
	public void set(String key,String value) {
		redisService.set(key,value);
	}

	@PostMapping("/set-timeout")
	public void setTimeout(String key,String value,long timeout) {
		redisService.set(key,value,timeout);
	}

	@GetMapping("/get")
	public String get(String key) {
		String value = redisService.get(key);
		return value;
	}

	@PostMapping("/set-hash")
	public void setHash(String key,String hashKey,String name,String password) {
		User user = new User();
		user.setName(name);
		user.setPassword(password);
		redisService.hset(key,hashKey, JSON.toJSONString(user));
	}

	@PostMapping("/set-hash-timeout")
	public void setHash(String key,String hashKey,String name,String password,long timeout) {
		User user = new User();
		user.setName(name);
		user.setPassword(password);
		redisService.hset(key,hashKey, JSON.toJSONString(user),timeout);
	}

	@GetMapping("/get-hash")
	public String getHash(String key,String hashKey) {
		String value = redisService.hget(key,hashKey);
		return value;
	}


}
