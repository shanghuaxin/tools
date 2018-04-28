package com.dingcheng.common.util;
/**
 * 一些常量
 */
public class SysConst {
	
	public static final Integer OPERATION_TIME = 5;	//一些更新操作动作默认缓存的秒数,用于防止重复提交
	public static final int REDIS_EXPIRE_TIME = 1000*60*60;
	public static final String LOGIN_FLAG = "system-login-flag";
	public static final String UPLOAD_FILE_BASE_URL = "http://192.168.70.105:8888/";
	/**
	 * 排序方式
	 * @author Administrator
	 *
	 */
	public enum SortBy{
		ASC,DESC;
	}
}
