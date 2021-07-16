package com.number47.white.blog.exception;

import com.number47.white.blog.common.CommonResult;
import com.number47.white.blog.common.ResultCode;
import org.apache.shiro.authc.AuthenticationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author number47
 * @date 2021/7/7 13:14
 * @description 重新写springboot错误的格式
 */
@RestController
public class ErrorController extends BasicErrorController {

	private static final Logger log = LoggerFactory.getLogger(ErrorController.class);

	public ErrorController() {
		super(new DefaultErrorAttributes(), new ErrorProperties());
	}

	/**
	 * produces 设置返回的数据类型：application/json
	 *
	 * @param request 请求
	 * @return 自定义的返回实体类
	 */
	@Override
	@RequestMapping(value = "", produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
		Map<String, Object> body = getErrorAttributes(request, isIncludeStackTrace(request, MediaType.ALL));
		HttpStatus status = getStatus(request);
		Exception exception = (Exception) request.getAttribute("exception");
		if (exception.getMessage() != null && exception.getMessage().contains(ResultCode.ILLEGAL_TOKEN.getMessage())){
			return new ResponseEntity(CommonResult.illegal(ResultCode.ILLEGAL_TOKEN.getMessage()), status);
		}
		if (exception.getMessage() != null && exception.getMessage().contains(ResultCode.UNAUTHORIZED.getMessage())){
			return new ResponseEntity(CommonResult.illegal(ResultCode.UNAUTHORIZED.getMessage()), status);
		}
		return new ResponseEntity(CommonResult.failed(exception.getMessage()), status);
	}

}
