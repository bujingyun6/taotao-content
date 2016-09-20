package cn.zhx.redis;

import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class TestClientRedis {

	@Test
	public void testJedis() throws Exception {
		// 第一步：创建一个Jedis对象。需要指定单机版redis服务的ip及端口号。
		Jedis jedis = new Jedis("192.168.20.132", 6379);
		// 第二步：调用jedis对象的方法操作redis数据库。
		jedis.set("mytest", "100");
		String result = jedis.get("mytest");
		// 第三步：打印结果
		System.out.println(result);
		// 第四步：关闭连接。
		jedis.close();
	}
	
	@Test
	public void testJedisPool() throws Exception {
		// 第一步：创建JedisPool对象，需要指定ip及端口号。
		JedisPool jedisPool = new JedisPool("192.168.20.132", 6379);
		// 第二步：从JedisPool对象获得连接Jedis对象。
		Jedis jedis = jedisPool.getResource();
		// 第三步：使用jedis对象的方法操作redis
		jedis.set("test2", "hello jedis");
		String result = jedis.get("test2");
		// 第四步：打印结果
		System.out.println(result);
		// 第五步：关闭Jedis对象。
		jedis.close();
		// 第六步：程序关闭时关闭JedisPool对象。
		jedisPool.close();
	}
	
}
