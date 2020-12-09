package com.number47.white.blog.exception;

import org.apache.shiro.authc.AuthenticationException;

/**
 * @author number47
 * @date 2020-10-14 00:45
 * @description token已经过期
 */
public class UnAuthorizedException extends AuthenticationException {

	public UnAuthorizedException() {
	}

	public UnAuthorizedException(String message) {
		super(message);
	}

	public UnAuthorizedException(String message, Throwable cause) {
		super(message, cause);
	}
}
