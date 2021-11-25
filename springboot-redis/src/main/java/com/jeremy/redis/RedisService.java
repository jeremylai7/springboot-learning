package com.jeremy.redis;

import java.util.List;
import java.util.Map;

/**
 * @Author: laizc
 * @Date: Created in  2021-11-24
 * @desc:
 */
public interface RedisService {
	//string
	/**
	 * 设置不过期的string值
	 * @param key     键
	 * @param value   值
	 */
	void set(String key,String value);

	/**
	 * 设置带过期时间的string值
	 * @param key       键
	 * @param value     值
	 * @param timeout   过期时间，单位秒
	 */
	void set(String key, String value, long timeout);

	/**
	 * 获取 string类型 值
	 * @param key
	 * @return
	 */
	String get(String key);

	//hash
	/**
	 * 将哈希表 key 中的字段 hashKey 的值设为 value ，过期时间为不过期
	 * @param key        键
	 * @param hashKey    hash键
	 * @param value      值
	 */
	void hSet(String key, String hashKey, String value);

	/**
	 * 获取哈希表指定字段的值
	 * @param key        键
	 * @param hashKey   hash键
	 * @return
	 */
	String hGet(String key, String hashKey);

	/**
	 * 是否存在哈希表指定的字段
	 * @param key       键
	 * @param hashKey  hash键
	 * @return
	 */
	boolean hExist(String key,String hashKey);

	/**
	 * 删除哈希指定的字段
	 * @param key      键
	 * @param hashKey  hash键
	 */
	void hDelete(String key,String... hashKey);

	/**
	 * 获取指定key 所有的字段和值
	 * @param key
	 * @return
	 */
	Map<Object,Object> hGetAll(String key);

	//list
	void lPush(String key,String value);

	List<String> lRange(String key, long start, long end);

	boolean delete(String key);



}
