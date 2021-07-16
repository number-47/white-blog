package com.number47.white.blog.filter;

import com.number47.white.blog.system.controller.UserController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * @author number47
 * @date 2021/7/7 13:47
 * @description 异常过滤器(捕获Filter异常，给Controller处理)
 */
public class ExceptionFilter implements Filter {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionFilter.class);


	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		try {
			chain.doFilter(request, response);
		} catch (Exception e) {
			LOGGER.error("捕获到异常",e);
			request.setAttribute("exception", e);
			request.getRequestDispatcher("/error").forward(request, response);
		}
	}
}
