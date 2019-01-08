package com.hhh.quality.platform.redis;

import java.util.Map;
import java.util.Set;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;

/**
 * Created by CPR210 on 2016/5/31.
 */
public interface RedisFactory {

  Long ttl(String key);

  String get(String key);

  /**
   * <p>向redis存入key和value,并释放连接资源</p>
   * <p>如果key已经存在 则覆盖</p>
   *
   * @param key
   * @param value
   *
   * @return 成功 返回OK 失败返回 0
   */
  String set(String key, String value);


  /**
   * 通过key 对value进行加值+1操作,
   * 当value不是int类型时会返回错误,
   * 当key不存在是则value为1
   * @param key
   * @return
   */
  Long incr(String key);

  Long decr(String key);

  /**
   * <p>设置key value并制定这个键值的有效期</p>
   *
   * @param key
   * @param value
   * @param seconds 单位:秒
   *
   * @return 成功返回OK 失败和异常返回null
   */
  String setex(String key, int seconds, String value);

  /**
   * 通过key同时设置 hash的多个field
   * @param key
   * @param hash
   * @return
   */
  String hmset(String key, Map<String, String> hash);


  Long zadd(String key, double score, String data);

  Set<String> zrevrange(String key, int start, int end);


  /**
   * <p>删除指定的key,也可以传入一个包含key的数组</p>
   *
   * @param keys 一个key  也可以使 string 数组
   *
   * @return 返回删除成功的个数
   */
  Long del(String... keys);

  ScanResult<String> sscan(String cursor, ScanParams params);

  /**
   * 加锁
   * @param key
   * @param timeout 毫秒
   * @param lockExpireTime 毫秒
   * @return
   */
  boolean lock(String key, long timeout, long lockExpireTime);

  public void unLock(String key);


  /**
   * 获取集合中元素个数
   * @param key
   * @return
   */
  Long zcard(String key);

  /**
   * 删除索引区间内的元素
   * @param key
   * @param start
   * @param end
   * @return
   */
  Long zremrangeByRank(String key, int start, int end);

}
