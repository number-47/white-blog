package com.number47.white.blog.common;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author number47
 * @date 2020-10-14 01:29
 * @description
 */
public class JwtToken implements AuthenticationToken {

	private static final long serialVersionUID = 1282057025599826155L;

	private String token;

	private String expireTime;

	public JwtToken(String token) {
		this.token = token;
	}

	public JwtToken(String token, String expireTime) {
		this.token = token;
		this.expireTime = expireTime;
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

	public String getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(String expireTime) {
		this.expireTime = expireTime;
	}
}