package com.number47.white.blog.util;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.number47.white.blog.constant.ShiroConstant;
import com.sun.org.apache.xml.internal.security.algorithms.SignatureAlgorithm;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author number47
 * @date 2020-10-14 00:22
 * @description JWT token 工具类,提供JWT生成,校验,工作
 */
@ConfigurationProperties(prefix = "dhb.jwt")
@Component
@Log4j2
public class JwtUtil {

	/**
	 * 校验 token是否正确
	 *
	 * @param token  密钥
	 * @param username 用户名
	 * @param secret 用户的密码
	 * @return 是否正确
	 */
	public static boolean verify(String token, String username, String secret) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);
			JWTVerifier verifier = JWT.require(algorithm)
					.withClaim("username", username)
					.build();
			//校验token
			DecodedJWT jwt = verifier.verify(token);
			log.info("token is valid" + jwt.toString());
			return true;
		} catch (Exception e) {
			log.error("token is invalid {}", e.getMessage());
			return false;
		}
	}

	/**
	 * 获得token中的信息无需secret解密也能获得
	 *
	 * @return token中包含的用户名
	 */
	public static String getUsername(String token) {
		if (StrUtil.isEmpty(token)){
			throw new AuthenticationException("token不能为空");
		}
		try {
			DecodedJWT jwt = JWT.decode(token);
			return jwt.getClaim("username").asString();
		} catch (JWTDecodeException e) {
			log.error("error：{}", e.getMessage());
			return null;
		}
	}

	/**
	 * 获得token中的信息无需secret解密也能获得
	 *
	 * @return token中包含的过期时间
	 */
	public static long getExp(String token) {
		try {
			DecodedJWT jwt = JWT.decode(token);
			return jwt.getExpiresAt().getTime();
		} catch (JWTDecodeException e) {
			log.error("error：{}", e.getMessage());
			return -1;
		}
	}

	/**
	 * 生成token签名EXPIRE_TIME 分钟后过期
	 *
	 * @param username 用户名
	 * @param secret   用户的密码
	 * @return token
	 */
	public static String sign(String username, String secret) {
		try {
			Date date = new Date(System.currentTimeMillis() + ShiroConstant.EXPIRE_TIME);
			Algorithm algorithm = Algorithm.HMAC256(secret);
			return JWT.create()
					.withClaim("username", username)
					.withExpiresAt(date)
					.sign(algorithm);
		} catch (Exception e) {
			log.error("error：{}", e);
			return null;
		}
	}

	/**
	 * 生成refreshToken签名EXPIRE_TIME 分钟后过期
	 * @return token
	 */
	public static String generalRefreshToken(String username) {
		try {
			Date date = new Date(System.currentTimeMillis() + ShiroConstant.REFRESH_EXPIRE_TIME);
			Algorithm algorithm = Algorithm.HMAC256("number47");
			return JWT.create()
					.withClaim("username", username)
					.withExpiresAt(date)
					.sign(algorithm);
		} catch (Exception e) {
			log.error("error：{}", e);
			return null;
		}
	}

	/**
	 *
	 * 校验token是否过期
	 * @param expireTime
	 * @return
	 */
	public static boolean isTokenExpired(Date expireTime){
		return expireTime.before(new Date());
	}
}
