package com.jeremy.redis;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Author: laizc
 * @Date: Created in  2021-11-24
 * @desc:
 */
public interface RedisService {

	/**
	 * 删除 key
	 * 实现命令： del key
	 * @param key 键
	 * @return    是否删除成功，失败一般是key不存在
	 */
	boolean delete(String key);

	//string
	/**
	 * 设置不过期的string值
	 * 实现命令：set key value
	 * @param key     键
	 * @param value   值
	 */
	void set(String key,String value);

	/**
	 * 设置带过期时间的string值
	 * 实现命令：set key value ex
	 * @param key       键
	 * @param value     值
	 * @param timeout   过期时间，单位秒
	 */
	void set(String key, String value, long timeout);

	/**
	 * 获取 string类型 值
	 * 实现命令：get key
	 * @param key
	 * @return
	 */
	String get(String key);

	//hash
	/**
	 * 将哈希表 key 中的字段 hashKey 的值设为 value ，过期时间为不过期
	 * 实现命令： HSET key field value
	 * @param key        键
	 * @param hashKey    hash键
	 * @param value      值
	 */
	void hSet(String key, String hashKey, String value);

	/**
	 * 获取哈希表指定字段的值
	 * 实现命令： HGET key field
	 * @param key        键
	 * @param hashKey   hash键
	 * @return
	 */
	String hGet(String key, String hashKey);

	/**
	 * 是否存在哈希表指定的字段
	 * 实现命令： HEXISTS key field
	 * @param key       键
	 * @param hashKey  hash键
	 * @return
	 */
	boolean hExist(String key,String hashKey);

	/**
	 * 删除哈希指定的字段
	 * 实现命令：HDEL key field [field ...]
	 * @param key      键
	 * @param hashKey  hash键
	 */
	void hDelete(String key,String... hashKey);

	/**
	 * 获取指定key 所有的字段和值
	 * 实现命令： HGETALL key
	 * @param key
	 * @return
	 */
	Map<Object,Object> hGetAll(String key);


	//list

	/**
	 * 从列表头部添加元素
	 * 实现命令： LPUSH key value [value ...]
	 * @param key     键
	 * @param value   值
	 */
	void lLeftPush(String key, String value);

	/**
	 * 从列表尾部添加元素
	 * 实现命令：RPUSH key value [value ...]
	 * @param key     键
	 * @param value   值
	 */
	void lRightPush(String key,String value);

	/**
	 * 从列表头部删除元素
	 * 实现命令：LPOP key
	 * @param key     键
	 */
	void lLeftPop(String key);

	/**
	 * 从列表尾部删除元素，并返回删除元素
	 * 实现命令：RPOP key
	 * @param key    键
	 */
	String lRightPop(String key);

	/**
	 * 获取列表值
	 * 实现命令：LRANGE key start stop
	 * @param key      键
	 * @param start    起始
	 * @param end      结束  -1 表示列表的最后一个元素， -2 表示列表的倒数第二个元素
	 * @return
	 */
	List<String> lRange(String key, long start, long end);

	/**
	 * 根据 count 值，移除列表中与参数 value 相等的元素
	 * 实现命令：LREM key count value
	 * @param key      键
	 * @param count   count > 0,从列表头部向尾部搜索，移除与 value 相等的元素，数量为 count
	 *                 count < 0,从列表尾部向头部搜索，移除与 value 相等的元素，数量为count 的绝对值
	 *                 count = 0，移除列表中所有与value 相等的元素
	 *
	 * @param value   值
	 */
	void lRemove(String key,long count,String value);

	//set

	/**
	 * set 添加单个元素
	 * 实现命令：SADD key member [member ...]
	 * @param key      键
	 * @param value    值
	 */
	void sAddAll(String key, String value);

	/**
	 * set 添加多个元素
	 * 实现命令：SADD key member [member ...]
	 * @param key      键
	 * @param value    值，多个用,隔开,egg:1,2,3
	 */
	void sAddAll(String key, String ... value);

	/**
	 * 获取 set 集合数量
	 * 实现命令：SCARD key
	 * @param key
	 * @return
	 */
	long sSize(String key);

	/**
	 * 获取set所有的值
	 * 实现命令： SMEMBERS key
	 * @param key
	 * @return
	 */
	Set<String> sMembers(String key);

	/**
	 * set 中是否存在 value
	 * 实现命令： SISMEMBER KEY VALUE
	 * @param key
	 * @param value
	 * @return
	 */
	boolean sExistMember(String key, String value);

	/**
	 * 移除 set 中的value 元素
	 * 实现命令：SREM KEY MEMBER1..MEMBERN
	 * @param key
	 * @param value
	 */
	void sRemove(String key,String... value);

	//sort set

	/**
	 * 向有序集合添加元素或者更新已存在的元素的分数
	 * 实现命令：ZADD KEY_NAME SCORE1 VALUE1
	 * @param key        键
	 * @param value      值
	 * @param score      分数
	 */
	void zsAdd(String key, String value, double score);

	/**
	 * 获取有序集合元素个数
	 * 实现命令：ZCARD KEY
	 * @param key
	 * @return
	 */
	long zsSize(String key);

	/**
	 * 获取下标内的有序集合
	 * 实现命令：ZRANGE key start stop
	 * @param key     键
	 * @param start   起始下标
	 * @param end     结算下标  -1 表示最后一个成员， -2 表示倒数第二个成员
	 * @return
	 */
	Set<String> zsRange(String key, long start, long end);


	/**
	 * 获取有序集合分数段内的元素
	 * 实现命令：ZRANGEBYSCORE key min max
	 * @param key     键
	 * @param min     分数最小值
	 * @param max     分数最大值
	 * @return
	 */
	Set<String> zsRangeByScore(String key,double min,double max);

	/**
	 * 获取分数区间内的元素个数
	 * 实现命令：ZCOUNT key min max
	 * @param key     键
	 * @param min     分数最小值
	 * @param max     分数最大值
	 * @return        元素个数
	 */
	long zsCount(String key,double min,double max);

	/**
	 * 在指定的元素上添加增量分数
	 * 实现命令：ZINCRBY key increment member
	 * @param key     键
	 * @param value   值
	 * @param score   分数，负数表示减
	 * @return         添加分数后的分数
	 */
	double zsAddScore(String key,String value,double score);

	/**
	 * 移除有序集合指定名称元素
	 * 实现命令：ZREM key member [member ...]
	 * @param key       键
	 * @param value     值
	 */
	void zsRemove(String key,String... value);

	/**
	 * 移除有序集合 分数内的元素
	 * 实现命令：ZRANGEBYSCORE key min max
	 * @param key       键
	 * @param min       分数的最小值，默认使用闭区间（大于等于），也可以使用开区间（大于），使用(表示，egg (5 表示大于5
	 * @param max       分数的最大值，默认使用闭区间（小于等于），也可以使用开区间（小于），使用(表示，egg (10 表示小于10
	 */
	void zsRemoveByScore(String key,double min,double max);
}
