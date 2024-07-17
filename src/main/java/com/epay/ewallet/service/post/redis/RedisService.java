package com.epay.ewallet.service.post.redis;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.epay.ewallet.service.post.config.AppConfig;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Component
public class RedisService {

	private static final Logger log = LogManager.getLogger(RedisService.class);
	
	@Autowired
	JedisPool pool;
	
	@Autowired
	AppConfig appConfig;
	
	public String getUserKey(String key) {
		Jedis redis = null;
		String userKey = "";
		try {
			redis = pool.getResource();
			redis.select(appConfig.REDIS_DB);
			userKey = redis.get("SETKEY" + key);
		} catch (Exception e) {
			e.printStackTrace();
			log.fatal("DeviceId: {} | Exception during get decrypt key", key, e);
		} finally {
			redis.close();
//			pool.returnResource(redis);
		}
		return userKey;
	}
}
