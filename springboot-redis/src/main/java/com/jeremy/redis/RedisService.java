package com.jeremy.redis;

import java.util.List;
import java.util.Map;

/**
 * @Author: laizc
 * @Date: Created in  2021-11-24
 * @desc:
 */
public interface RedisService {

	/**
	 * 删除 key
	 * @param key 键
	 * @return    是否删除成功，失败一般是key不存在
	 */
	boolean delete(String key);

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

	/**
	 * 从列表头部添加元素
	 * @param key     键
	 * @param value   值
	 */
	void lLeftPush(String key, String value);

	/**
	 * 从列表尾部添加元素
	 * @param key     键
	 * @param value   值
	 */
	void lRightPush(String key,String value);

	/**
	 * 从列表头部删除元素
	 * @param key     键
	 */
	void lLeftPop(String key);

	/**
	 * 从列表尾部删除元素，并返回删除元素
	 * @param key    键
	 */
	String lRightPop(String key);

	/**
	 * 获取列表值
	 * @param key      键
	 * @param start    起始
	 * @param end      结束  -1 表示列表的最后一个元素， -2 表示列表的倒数第二个元素
	 * @return
	 */
	List<String> lRange(String key, long start, long end);

	/**
	 * 根据 count 值，移除列表中与参数 value 相等的元素
	 * @param key      键
	 * @param count   count > 0,从列表头部向尾部搜索，移除与 value 相等的元素，数量为 count
	 *                 count < 0,从列表尾部向头部搜索，移除与 value 相等的元素，数量为count 的绝对值
	 *                 count = 0，移除列表中所有与value 相等的元素
	 *
	 * @param value   值
	 */
	void lRemove(String key,long count,String value);





}
