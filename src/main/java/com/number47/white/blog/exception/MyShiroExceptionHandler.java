package com.number47.white.blog.exception;

import com.number47.white.blog.common.CommonResult;
import com.number47.white.blog.common.ResultCode;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author number47
 * @date 2020-10-13 22:14
 * @description 自定义异常
 */
@ControllerAdvice
public class MyShiroExceptionHandler {

	/**
	 * 处理AuthenticationException异常
	 * @return
	 */
	@ResponseBody
	@ExceptionHandler(value = AuthenticationException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public CommonResult authenticationExceptionHandler(Exception e){
		return CommonResult.failed(ResultCode.ILLEGAL_TOKEN,e.getMessage());
	}

	/**
	 * 处理UnAuthorizedException异常
	 * @return
	 */
	@ResponseBody
	@ExceptionHandler(value = UnAuthorizedException.class)
	@ResponseStatus(HttpStatus.OK)
	public CommonResult unAuthorizedExceptionHandler(Exception e){
		return CommonResult.failed(ResultCode.UNAUTHORIZED,e.getMessage());
	}

	/**
	 * 处理FailRequestException异常
	 * @return
	 */
	@ResponseBody
	@ExceptionHandler(value = FailRequestException.class)
	public CommonResult FailRequestException(Exception e){
		return CommonResult.failed(e.getMessage());
	}
}
