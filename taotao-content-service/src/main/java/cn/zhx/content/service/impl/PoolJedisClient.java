package cn.zhx.content.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import cn.zhx.service.JedisClient;

/**   redis連接池單機版
 * @author zhaohx
 *
 */
public class PoolJedisClient implements JedisClient {

	
	private JedisPool jedisPool;
	
	public void setJedisPool(JedisPool jedisPool) {
		this.jedisPool = jedisPool;
	}

	@Override
	public String set(String key, String value) {
		Jedis jedis = jedisPool.getResource();
		String result = jedis.set(key, value);
		jedis.close();
		return result;
	}

	@Override
	public String get(String key) {
		Jedis jedis = jedisPool.getResource();
		String string = jedis.get(key);
		jedis.close();
		return string;
	}

	@Override
	public Long hset(String key, String item, String value) {
		Jedis jedis = jedisPool.getResource();
		Long string = jedis.hset(key, item, value);
		jedis.close();
		return string;
	}

	@Override
	public String hget(String key, String item) {
		Jedis jedis = jedisPool.getResource();
		String string = jedis.hget(key, item);
		jedis.close();
		return string;
	}

	@Override
	public Long incr(String key) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.incr(key);
		jedis.close();
		return result;
	}

	@Override
	public Long decr(String key) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.decr(key);
		jedis.close();
		return result;
	}

	@Override
	public Long expire(String key, int second) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.expire(key, second);
		jedis.close();
		return result;
	}

	@Override
	public Long ttl(String key) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.ttl(key);
		jedis.close();
		return result;
	}

}
