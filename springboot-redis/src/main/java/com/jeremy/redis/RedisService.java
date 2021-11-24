package com.jeremy.redis;

/**
 * @Author: laizc
 * @Date: Created in  2021-11-24
 * @desc:
 */
public interface RedisService {

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
}
