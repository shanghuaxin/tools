package com.dingcheng.redis.util;

import java.util.Map;

import org.apache.log4j.Logger;

public class RedisSessionUtils {
	private static Logger logger = Logger.getLogger(RedisSessionUtils.class);
	private static final Integer DEFAULT_SESSION_TIMEOUT = Integer.valueOf(1800);

	public static boolean saveSession(String sessionId, Map<String, Object> sessionObj, Integer seconds) {
		boolean result = false;
		try {
			if (seconds != null) {
				result = RedisUtils.save(sessionId, sessionObj, seconds.intValue()).booleanValue();
			} else {
				result = RedisUtils.save(sessionId, sessionObj, DEFAULT_SESSION_TIMEOUT.intValue()).booleanValue();
			}
		} catch (Exception e) {
			logger.error("              " + e);
		}
		return result;
	}

	public static boolean deleteSession(String sessionId) {
		boolean result = false;
		try {
			result = RedisUtils.del(sessionId).booleanValue();
		} catch (Exception e) {
			logger.error("              " + e);
		}
		return result;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Map<String, Object> getSession(String sessionId) {
		Map<String, Object> sessionObj = null;
		try {
			sessionObj = (Map) RedisUtils.get(sessionId);
		} catch (Exception e) {
			logger.error("              " + e);
		}
		return sessionObj;
	}

	public static boolean updateSessionTime(String sessionId, Integer seconds) {
		boolean result = false;
		try {
			if (seconds == null) {
				result = RedisUtils.expire(sessionId, DEFAULT_SESSION_TIMEOUT.intValue()).booleanValue();
			} else {
				result = RedisUtils.expire(sessionId, seconds.intValue()).booleanValue();
			}
		} catch (Exception e) {
			logger.error("                  " + e);
		}
		return result;
	}
}
