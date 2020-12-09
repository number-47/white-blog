package com.number47.white.blog.exception;

/**
 * @author number47
 * @date 2020-10-14 00:45
 * @description
 */
public class FailRequestException extends RuntimeException{

	public FailRequestException() {
	}

	public FailRequestException(String message) {
		super(message);
	}

	public FailRequestException(String message, Throwable cause) {
		super(message, cause);
	}
}
