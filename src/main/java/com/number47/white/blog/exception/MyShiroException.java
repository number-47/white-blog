package com.number47.white.blog.exception;

import com.alibaba.druid.wall.violation.ErrorCode;
import com.number47.white.blog.common.CommonResult;
import com.number47.white.blog.common.ResultCode;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * @author number47
 * @date 2020-10-13 22:14
 * @description 自定义异常
 */
@ControllerAdvice
public class MyShiroException {
	/**
	 * 处理shiro权限拦截异常
	 * @return
	 */
	@ResponseBody
	@ExceptionHandler(value = AuthorizationException.class)
	public CommonResult<String> defaultErrorHandler(){
		return CommonResult.failed("权限不足");
	}

	/**
	 * 处理UnAuthorizedException异常
	 * @return
	 */
	@ResponseBody
	@ExceptionHandler(value = UnAuthorizedException.class)
	public CommonResult unAuthorizedExceptionHandler(String message){
		return CommonResult.failed(ResultCode.UNAUTHORIZED,message);
	}
}
