package com.number47.white.blog.filter;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;
import java.util.Objects;

/**
 * @program: white-blog
 * @description:
 * @author: number47
 * @create: 2020-12-08 10:55
 **/
@Slf4j
@Component
public class ExceptionFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("异常Filter初始化");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        try {
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            // 发生异常，保存异常栈
            request.setAttribute("filter.error", e);
            request.getRequestDispatcher("/error/exthrow").forward(request, response);
        }
    }

    @Override
    public void destroy() {

    }
}
