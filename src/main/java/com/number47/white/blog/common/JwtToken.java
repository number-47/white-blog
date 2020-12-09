package com.number47.white.blog.common;

import com.number47.white.blog.constant.ShiroConstant;
import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author number47
 * @date 2020-10-14 01:29
 * @description
 */
public class JwtToken implements AuthenticationToken {

	private static final long serialVersionUID = 1282057025599826155L;

	private String token;

	private String refreshToken;

	private long expireTime;

	private long refreshExpireTime;

	public JwtToken(String token) {
		this.token = token;
	}

	public JwtToken(String token, String refreshToken) {
		this.token = token;
		this.expireTime = System.currentTimeMillis() + ShiroConstant.EXPIRE_TIME;
		this.refreshToken = refreshToken;
		this.refreshExpireTime = System.currentTimeMillis() + ShiroConstant.REFRESH_EXPIRE_TIME;
	}

	@Override
	public Object getPrincipal() {
		return token;
	}

	@Override
	public Object getCredentials() {
		return token;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public long getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(long expireTime) {
		this.expireTime = expireTime;
	}

	public long getRefreshExpireTime() {
		return refreshExpireTime;
	}

	public void setRefreshExpireTime(long refreshExpireTime) {
		this.refreshExpireTime = refreshExpireTime;
	}
}
