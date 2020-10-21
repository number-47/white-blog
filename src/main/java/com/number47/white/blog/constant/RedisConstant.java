package com.number47.white.blog.constant;

/**
 * @author number47
 * @date 2020-10-13 22:28
 * @description redis常量类
 */
public class RedisConstant {
	/**
	 * TOKEN前缀
	 */
	public static final String REDIS_PREFIX_LOGIN = "login_token_%s";
	/**
	 * 过期时间2小时
	 */
	public static final Integer REDIS_EXPIRE_TWO = 7200;
	/**
	 * 过期时间15分
	 */
	public static final Integer REDIS_EXPIRE_EMAIL = 900;
	/**
	 * 过期时间5分钟
	 */
	public static final Integer REDIS_EXPIRE_KAPTCHA = 300;
	/**
	 * 暂无过期时间
	 */
	public static final Integer REDIS_EXPIRE_NULL = -1;
}
