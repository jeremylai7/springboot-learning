package com.jeremy.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @Author: laizc
 * @Date: Created in  2021-11-24
 * @desc:
 */

@Component
public class RedisServiceImpl implements RedisService{

	@Autowired
	private StringRedisTemplate redisTemplate;

	@Override
	public void set(String key, String value) {
		redisTemplate.opsForValue().set(key,value);
	}

	@Override
	public void set(String key, String value, long timeout) {
		redisTemplate.opsForValue().set(key,value,timeout, TimeUnit.SECONDS);
	}

	@Override
	public String get(String key) {
		return redisTemplate.opsForValue().get(key);
	}

	@Override
	public void hset(String key, String hashKey,String value) {
		redisTemplate.opsForHash().put(key,hashKey,value);
	}

	@Override
	public void hset(String key, String hashKey,String value, long timeout) {
		redisTemplate.opsForHash().put(key,hashKey,value);
	}

	@Override
	public String hget(String key, String hashKey) {
		return (String) redisTemplate.opsForHash().get(key,hashKey);
	}
}
