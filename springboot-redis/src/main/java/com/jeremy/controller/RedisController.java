package com.jeremy.controller;

import com.alibaba.fastjson.JSON;
import com.jeremy.model.User;
import com.jeremy.redis.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Author: laizc
 * @Date: Created in  2021-11-24
 * @desc:
 */
@RestController
public class RedisController {

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

	@PostMapping("/l-left-push")
	public void lPush(String key,String value) {
		redisService.lLeftPush(key,value);
	}

	@PostMapping("/l-right-pop")
	public String lRightPop(String key) {
		return redisService.lRightPop(key);
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

	@PostMapping("/lremove")
	public void lRemove(String key,long count,String value) {
		redisService.lRemove(key,count,value);
	}

	@PostMapping("/sadd")
	public void sAdd(String key,String value) {
		redisService.sAddAll(key,value);
	}

	/**
	 *
	 * @param key
	 * @param value  多个value 用逗号隔开 egg: 1,2,3,4
	 */
	@PostMapping("/s-add-all")
	public void sAddAll(String key,String... value) {
		redisService.sAddAll(key,value);
	}

	@GetMapping("/ssize")
	public long sSize(String key) {
		return redisService.sSize(key);
	}

	@GetMapping("/s-members")
	public Set<String> sMembers(String key) {
		Set<String> set = redisService.sMembers(key);
		return set;
	}

	@PostMapping("/zs-add")
	public void zsAdd(String key,String value,double score) {
		redisService.zsAdd(key,value,score);
	}

	@GetMapping("/zs-size")
	public long zsSize(String key) {
		long size = redisService.zsSize(key);
		return size;
	}

	@GetMapping("/zs-range")
	public Set<String> zsRange(String key,long start,long end) {
		Set<String> set = redisService.zsRange(key,start,end);
		return set;
	}

	@PostMapping("/zs-add-score")
	public double zsAddScore(String key,String value,double score) {
		return redisService.zsAddScore(key,value,score);
	}

}
