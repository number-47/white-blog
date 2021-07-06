package com.number47.white.blog.util;

import com.number47.white.blog.constant.CommonConstant;
import com.number47.white.blog.constant.ShiroConstant;
import com.number47.white.blog.system.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;

import java.security.MessageDigest;

/**
 * @author number47
 * @date 2020-10-13 22:23
 * @description Shiro工具类
 */
public class ShiroUtils {
	/**
	 * 私有构造器
	 **/
	private ShiroUtils() {
	}

	/**
	 * 用户登出
	 *
	 * @Author number47
	 * @CreateTime 2019/6/17 17:23
	 */
	public static void logout() {
		SecurityUtils.getSubject().logout();
	}

	/**
	 * 获取当前用户信息
	 *
	 * @Author number47
	 * @CreateTime 2019/6/17 17:03
	 * @Return SysUserEntity 用户信息
	 */
	public static User getUserInfo() {
		return (User) SecurityUtils.getSubject().getPrincipal();
	}

	/**
	 * 密码加密
	 * @param password
	 * @return
	 */
	public static String passwordEncrypt(String password){
		// 生成盐,默认长度 16 位
		String salt = new SecureRandomNumberGenerator().nextBytes().toString();
		// 得到 hash 后的密码
		String encodedPassword = new SimpleHash(ShiroConstant.ALGORITHM_NAME, password, salt, ShiroConstant.HASH_TIME).toString();
		return encodedPassword;
	}

	/**
	 * 密码加密,返回加密和盐
	 * @param password
	 * @return
	 */
	public static SimpleHash passwordAndSalt(String password){
		// 生成盐,默认长度 16 位
		String salt = new SecureRandomNumberGenerator().nextBytes().toString();
		// 得到 hash 后的密码
		SimpleHash simpleHash = new SimpleHash(ShiroConstant.ALGORITHM_NAME, password, salt, ShiroConstant.HASH_TIME);
		return simpleHash;
	}

	/**
	 * 密码验证
	 * @param password 未加密密码
	 * @param encodedPassword 数据库已加密的密码
	 * @return
	 */
	public static boolean passwordDecrypt(String password, String encodedPassword){
		byte[] passwordByte = passwordEncrypt(password).getBytes();
		byte[] encodedPasswordByte = encodedPassword.getBytes();
		return 	MessageDigest.isEqual(passwordByte, encodedPasswordByte);
	}
}
