package com.dingcheng.redis.util;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import redis.clients.jedis.BinaryJedisCommands;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.ShardedJedis;
import redis.clients.util.Pool;

public class RedisUtils {
	private static Logger logger = Logger.getLogger(RedisUtils.class);
	private static Pool<BinaryJedisCommands> jedisPool;

	public static Pool<BinaryJedisCommands> getJedisPool() {
		return jedisPool;
	}

	public void setJedisPool(Pool<BinaryJedisCommands> jedisPool) {
		RedisUtils.jedisPool = jedisPool;
	}

	private static void releaseResource(BinaryJedisCommands jedis) {
		if (jedis != null) {
			jedisPool.returnResource(jedis);
		}
	}

	public static void flushAll() {
		BinaryJedisCommands jedis = null;
		try {
			jedis = (BinaryJedisCommands) jedisPool.getResource();
			if (!(jedis instanceof ShardedJedis)) {
				if ((jedis instanceof Jedis)) {
					((Jedis) jedis).flushAll();
				}
			}
		} catch (Exception e) {
			logger.error("Cache          " + e);
		} finally {
			releaseResource(jedis);
		}
	}

	public static Boolean save(Object key, Object object) {
		return save(key, object, -1);
	}

	public static Boolean save(Object key, Object object, int seconds) {
		BinaryJedisCommands jedis = null;
		try {
			jedis = (BinaryJedisCommands) jedisPool.getResource();
			jedis.set(SerializeUtils.serialize(key), SerializeUtils.serialize(object));
			if(seconds != -1) {
				jedis.expire(SerializeUtils.serialize(key), seconds);
			}
			return Boolean.valueOf(true);
		} catch (Exception e) {
			logger.error("Cache          " + e);
			return Boolean.valueOf(false);
		} finally {
			releaseResource(jedis);
		}
	}

	public static Object get(Object key) {
		BinaryJedisCommands jedis = null;
		try {
			jedis = (BinaryJedisCommands) jedisPool.getResource();
			byte[] obj = jedis.get(SerializeUtils.serialize(key));
			return obj == null ? null : SerializeUtils.unSerialize(obj);
		} catch (Exception e) {
			logger.error("Cache          " + e);
			return null;
		} finally {
			releaseResource(jedis);
		}
	}

	public static Boolean del(Object key) {
		BinaryJedisCommands jedis = null;
		try {
			jedis = (BinaryJedisCommands) jedisPool.getResource();
			jedis.del(SerializeUtils.serialize(key));
			return Boolean.valueOf(true);
		} catch (Exception e) {
			logger.error("Cache          " + e);
			return Boolean.valueOf(false);
		} finally {
			releaseResource(jedis);
		}
	}

	public static Boolean del(Object... keys) {
		BinaryJedisCommands jedis = null;
		try {
			jedis = (BinaryJedisCommands) jedisPool.getResource();
			jedis.del(SerializeUtils.serialize(keys));
			return Boolean.valueOf(true);
		} catch (Exception e) {
			logger.error("Cache          " + e);
			return Boolean.valueOf(false);
		} finally {
			releaseResource(jedis);
		}
	}

	public static Boolean expire(Object key, int seconds) {
		BinaryJedisCommands jedis = null;
		try {
			jedis = (BinaryJedisCommands) jedisPool.getResource();
			jedis.expire(SerializeUtils.serialize(key), seconds);
			return Boolean.valueOf(true);
		} catch (Exception e) {
			logger.error("Cache                  " + e);
			return Boolean.valueOf(false);
		} finally {
			releaseResource(jedis);
		}
	}

	public static Boolean addHash(String key, Object field, Object value) {
		BinaryJedisCommands jedis = null;
		try {
			jedis = (BinaryJedisCommands) jedisPool.getResource();
			jedis.hset(SerializeUtils.serialize(key), SerializeUtils.serialize(field), SerializeUtils.serialize(value));
			return Boolean.valueOf(true);
		} catch (Exception e) {
			logger.error("Cache          " + e);
			return Boolean.valueOf(false);
		} finally {
			releaseResource(jedis);
		}
	}

	public static Object getHash(Object key, Object field) {
		BinaryJedisCommands jedis = null;
		try {
			jedis = (BinaryJedisCommands) jedisPool.getResource();
			byte[] obj = jedis.hget(SerializeUtils.serialize(key), SerializeUtils.serialize(field));
			return SerializeUtils.unSerialize(obj);
		} catch (Exception e) {
			logger.error("Cache          " + e);
			return null;
		} finally {
			releaseResource(jedis);
		}
	}

	public static Boolean delHash(Object key, Object field) {
		BinaryJedisCommands jedis = null;
		try {
			jedis = (BinaryJedisCommands) jedisPool.getResource();
			long result = jedis.hdel(SerializeUtils.serialize(key), new byte[][] { SerializeUtils.serialize(field) })
					.longValue();
			return Boolean.valueOf(result == 1L);
		} catch (Exception e) {
			logger.error("Cache          " + e);
			return null;
		} finally {
			releaseResource(jedis);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Set<byte[]> keys(String pattern) {
		BinaryJedisCommands jedis = null;
		try {
			jedis = (BinaryJedisCommands) jedisPool.getResource();
			Set<byte[]> allKey = null;
			if (!(jedis instanceof ShardedJedis)) {
				if ((jedis instanceof Jedis)) {
					allKey = ((Jedis) jedis).keys(("*" + pattern + "*").getBytes());
				}
			}
			return allKey;
		} catch (Exception e) {
			logger.error("Cache          " + e);
			return new HashSet();
		} finally {
			releaseResource(jedis);
		}
	}

	public static Map<byte[], byte[]> getAllHash(Object key) {
		BinaryJedisCommands jedis = null;
		try {
			jedis = (BinaryJedisCommands) jedisPool.getResource();
			Map<byte[], byte[]> map = jedis.hgetAll(SerializeUtils.serialize(key));
			return map;
		} catch (Exception e) {
			logger.error("Cache          " + e);
			return null;
		} finally {
			releaseResource(jedis);
		}
	}

	public static Boolean exists(Object key) {
		BinaryJedisCommands jedis = null;
		Boolean result = Boolean.valueOf(false);
		try {
			jedis = (BinaryJedisCommands) jedisPool.getResource();
			result = jedis.exists(SerializeUtils.serialize(key));
			return result;
		} catch (Exception e) {
			logger.error("Cache          " + e);
			return Boolean.valueOf(false);
		} finally {
			releaseResource(jedis);
		}
	}

	public static long hincrBy(Object key, Object field, long num) {
		BinaryJedisCommands jedis = null;
		long localObject1 = -1L;
		try {
			jedis = (BinaryJedisCommands) jedisPool.getResource();

			localObject1 = jedis.hincrBy(SerializeUtils.serialize(key), SerializeUtils.serialize(field), num)
					.longValue();

			return localObject1;
		} catch (Exception e) {
			logger.error("Cache          " + e);
			localObject1 = -1L;

			return localObject1;
		} finally {
			releaseResource(jedis);
		}
	}
}
