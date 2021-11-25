package com.jeremy.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
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
	public boolean delete(String key) {
		return redisTemplate.delete(key);
	}

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
	public void hSet(String key, String hashKey, String value) {
		redisTemplate.opsForHash().put(key,hashKey,value);
	}

	@Override
	public String hGet(String key, String hashKey) {
		return (String) redisTemplate.opsForHash().get(key,hashKey);
	}

	@Override
	public boolean hExist(String key, String hashKey) {
		return redisTemplate.opsForHash().hasKey(key,hashKey);
	}

	@Override
	public void hDelete(String key, String... hashKey) {
		redisTemplate.opsForHash().delete(key,hashKey);
	}

	@Override
	public Map<Object,Object> hGetAll(String key) {
		Map<Object,Object> map = redisTemplate.opsForHash().entries(key);
		return map;
	}

	@Override
	public void lLeftPush(String key, String value) {
		redisTemplate.opsForList().leftPush(key,value);
	}

	@Override
	public void lRightPush(String key, String value) {
		redisTemplate.opsForList().rightPush(key,value);
	}

	@Override
	public void lLeftPop(String key) {
		redisTemplate.opsForList().leftPop(key);
	}

	@Override
	public String lRightPop(String key) {
		String lastValue = redisTemplate.opsForList().rightPop(key);
		return lastValue;
	}

	@Override
	public List<String> lRange(String key, long start, long end) {
		List<String> list = redisTemplate.opsForList().range(key,start,end);
		return list;
	}

	@Override
	public void lRemove(String key, long count, String value) {
		redisTemplate.opsForList().remove(key,count,value);
	}


}
