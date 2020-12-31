package com.number47.white.blog.exception;

/**
 * @author number47
 * @date 2020-10-14 00:45
 * @description
 */
public class SystemErrorException extends RuntimeException{

	public SystemErrorException() {
	}

	public SystemErrorException(String message) {
		super(message);
	}

	public SystemErrorException(String message, Throwable cause) {
		super(message, cause);
	}
}
