package com.number47.white.blog.constant;

/**
 * @author number47
 * @date 2020-10-13 23:17
 * @description
 */
public class ShiroConstant {
	public static final String AUTHORIZATION = "Authorization";
	/**
	 * 加密的字符串,相当于签名
	 */
	public static final String TOKEN = "Token";
	/**
	 * 登录地址
	 */
	public static final String LOGIN_URL = "/api/user/login";
	/**
	 * 加密hash 算法迭代次数
	 */
	public static final int HASH_TIME=2;
	/**
	 * 加密算法
	 */
	public static final String ALGORITHM_NAME = "md5";

	public static final long EXPIRE_TIME = 30 * 60 * 1000;
}
