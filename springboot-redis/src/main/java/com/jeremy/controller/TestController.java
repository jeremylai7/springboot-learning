package com.jeremy.controller;

import com.alibaba.fastjson.JSON;
import com.jeremy.User;
import com.jeremy.redis.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

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
		redisService.hSet(key,hashKey, JSON.toJSONString(user));
	}

	@GetMapping("/get-hash")
	public String getHash(String key,String hashKey) {
		String value = redisService.hGet(key,hashKey);
		return value;
	}

	@GetMapping("/get-all-hash")
	public String getAllHash(String key) {
		Map<Object,Object> map = redisService.hGetAll(key);
		return JSON.toJSONString(map);
	}

	@PostMapping("/lpush")
	public void lPush(String key,String value) {
		redisService.lPush(key,value);
	}

	@GetMapping("/lrange")
	public List<String> lRange(String key,long start,long end) {
		List<String> list = redisService.lRange(key,start,end);
		return list;
	}

	@GetMapping("/lrange-all")
	public List<String> lRangeAll(String key) {
		List<String> list = redisService.lRange(key,0,-1);
		return list;
	}

	@PostMapping("/delete")
	public boolean delete(String key) {
		return redisService.delete(key);
	}


}
