package com.number47.white.blog.exception;

/**
 * @author number47
 * @date 2020-10-14 00:45
 * @description
 */
public class UnAuthorizedException extends RuntimeException{

	public UnAuthorizedException() {
	}

	public UnAuthorizedException(String message) {
		super(message);
	}

	public UnAuthorizedException(String message, Throwable cause) {
		super(message, cause);
	}
}
