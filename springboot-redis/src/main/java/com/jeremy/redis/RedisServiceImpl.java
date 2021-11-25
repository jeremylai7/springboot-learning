package com.jeremy.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
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

	@Override
	public void sAddAll(String key, String value) {
		redisTemplate.opsForSet().add(key,value);
	}

	@Override
	public void sAddAll(String key, String... value) {
		redisTemplate.opsForSet().add(key,value);
	}

	@Override
	public long sSize(String key) {
		return redisTemplate.opsForSet().size(key);
	}

	@Override
	public Set<String> sMembers(String key) {
		Set<String> set = redisTemplate.opsForSet().members(key);
		return set;
	}

	@Override
	public boolean sExistMember(String key, String value) {
		return redisTemplate.opsForSet().isMember(key,value);
	}

	@Override
	public void sRemove(String key, String... value) {
		redisTemplate.opsForSet().remove(key,value);
	}

	@Override
	public void zsAdd(String key, String value, double score) {
		redisTemplate.opsForZSet().add(key,value,score);

	}

	@Override
	public long zsSize(String key) {
		return redisTemplate.opsForZSet().size(key);
	}

	@Override
	public Set<String> zsRange(String key, long start, long end) {
		Set<String> set = redisTemplate.opsForZSet().range(key,start,end);
		return set;
	}

	@Override
	public Set<String> zsRangeByScore(String key,double min,double max) {
		Set<String> set = redisTemplate.opsForZSet().rangeByScore(key,min,max);
		return set;
	}

	@Override
	public long zsCount(String key, double min, double max) {
		return redisTemplate.opsForZSet().count(key,min,max);
	}

	@Override
	public double zsAddScore(String key, String value, double score) {
		return redisTemplate.opsForZSet().incrementScore(key,value,score);
	}

	@Override
	public void zsRemove(String key,String... value) {
		redisTemplate.opsForZSet().remove(key,value);
	}

	@Override
	public void zsRemoveByScore(String key, double min, double max) {
		redisTemplate.opsForZSet().removeRangeByScore(key,min,max);
	}


}
