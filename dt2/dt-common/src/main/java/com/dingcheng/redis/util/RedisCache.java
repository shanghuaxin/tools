package com.dingcheng.redis.util;

import java.util.concurrent.Callable;

import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;
import org.springframework.stereotype.Component;

@Component("redisCache")
public class RedisCache implements Cache {
	private String name;
	public RedisUtils cache = new RedisUtils();

	public void clear() {
		RedisUtils.flushAll();
	}

	public void evict(Object key) {
		RedisUtils.del(key);
	}

	public Cache.ValueWrapper get(Object key) {
		Cache.ValueWrapper result = null;
		Object thevalue = RedisUtils.get(key);
		if (thevalue != null) {
			result = new SimpleValueWrapper(thevalue);
		}
		return result;
	}

	public String getName() {
		return this.name;
	}

	public Object getNativeCache() {
		return this.cache;
	}

	public void put(Object arg0, Object arg1) {
		RedisUtils.save(arg0, arg1, 20000);
	}

	public RedisCache() {
	}

	public RedisCache(String name) {
		this.name = name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public <T> T get(Object arg0, Class<T> arg1) {
		return null;
	}

	@Override
	public ValueWrapper putIfAbsent(Object arg0, Object arg1) {
		return null;
	}

	@Override
	public <T> T get(Object arg0, Callable<T> arg1) {
		// TODO Auto-generated method stub
		return null;
	}

}