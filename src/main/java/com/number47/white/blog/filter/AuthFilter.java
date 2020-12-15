package com.number47.white.blog.filter;

import com.number47.white.blog.common.JwtToken;
import com.number47.white.blog.constant.ShiroConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.Filter;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

/**
 * @author number47
 * @date 2020-10-13 23:47
 * @description 对每一个请求都进行token的校验
 */
@Slf4j
@Component("authFilter")
public class AuthFilter extends BasicHttpAuthenticationFilter implements Filter {

	private String[] skipAuthUrl = {"/api/user/login",
			"/api/user/register",
			"/api/user/refreshToken"};

	/**
	 * 执行登录
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@Override
	protected boolean executeLogin(ServletRequest request, ServletResponse response) throws AuthenticationException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		// 从请求头获取“Authorization”的值,去除bearer
		String authorization = httpServletRequest.getHeader(ShiroConstant.AUTHORIZATION);
		String token = authorization.replace("bearer ","");
		JwtToken jwtToken = new JwtToken(token);
		// 提交给realm进行登入，如果错误他会抛出异常并被捕获
		getSubject(request, response).login(jwtToken);
		// 如果没有抛出异常则代表登入成功，返回true
		return true;

	}

	/**
	 * 执行登录认证
	 *
	 * @param request
	 * @param response
	 * @param mappedValue
	 * @return
	 */
	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws AuthenticationException{
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		List<String> urls = Arrays.asList(skipAuthUrl);
		//不需要登录校验的请求接口
		if (urls.contains(httpServletRequest.getRequestURI())) {
			return true;
		}
		return executeLogin(request, response);
	}


	/**
	 * 对跨域提供支持
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@Override
	protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
		httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
		httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
		httpServletResponse.setHeader("WWW-Authenticate", "Basic realm='Realm'");
		// 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
		if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
			httpServletResponse.setStatus(HttpStatus.OK.value());
			return false;
		}
		return super.preHandle(request, response);
	}

}
