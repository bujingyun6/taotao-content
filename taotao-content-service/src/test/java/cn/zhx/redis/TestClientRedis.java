package cn.zhx.redis;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.zhx.service.JedisClient;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
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
	
	@Test
	public void testJedisCluster() throws Exception {
		//节点地址列表
		Set<HostAndPort> nodes = new HashSet<>();
		nodes.add(new HostAndPort("192.168.20.132", 7001));
		nodes.add(new HostAndPort("192.168.20.132", 7002));
		nodes.add(new HostAndPort("192.168.20.132", 7003));
		nodes.add(new HostAndPort("192.168.20.132", 7004));
		nodes.add(new HostAndPort("192.168.20.132", 7005));
		nodes.add(new HostAndPort("192.168.20.132", 7006));
		// 1、创建一个JedisCluster对象，构造方法，需要指定一个Set对象，set中包含HostAndPort对象。每个HostAndPort就是一个节点的地址。
		JedisCluster jedisCluster = new JedisCluster(nodes);
		// 2、直接使用JedisCluster操作redis数据库。
		jedisCluster.set("jedisCluster-test", "123456");
		String result = jedisCluster.get("jedisCluster-test");
		// 3、打印结果
		System.out.println(result);
		// 4、程序结束时关闭JedisCluster
		jedisCluster.close();
	}
	
	@Test
	public void testPoolJedisXml(){
		ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-redis.xml");
		JedisClient bean =  (JedisClient) ac.getBean("jedisClient");
		bean.set("xmltest", "3435");
		System.out.println(bean.get("xmltest"));
	}
}
