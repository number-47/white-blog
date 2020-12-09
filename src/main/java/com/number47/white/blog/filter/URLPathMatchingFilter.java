package com.number47.white.blog.filter;

import cn.hutool.http.HttpStatus;
import com.number47.white.blog.system.service.AdminPermissionService;
import com.number47.white.blog.util.SpringContextUtils;
import io.swagger.models.HttpMethod;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.PathMatchingFilter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

/**
 * @program: white-blog
 * @description:
 * @author: number47
 * @create: 2020-09-11 17:01
 **/
@Log4j2
public class URLPathMatchingFilter extends PathMatchingFilter {

    @Autowired
    private AdminPermissionService adminPermissionService;

    private String[] skipAuthUrl = {"/api/user/login",
            "/api/user/register",
            "/api/user/refreshToken"};

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        if (HttpMethod.OPTIONS.toString().equals((httpServletRequest).getMethod())) {
            httpServletResponse.setStatus(HttpStatus.HTTP_NO_CONTENT);
            return true;
        }
        List<String> urls = Arrays.asList(skipAuthUrl);
        //不需要登录校验的请求接口
        if (urls.contains(httpServletRequest.getRequestURI())) {
            return true;
        }
        if (adminPermissionService == null) {
            adminPermissionService = SpringContextUtils.getContext().getBean(AdminPermissionService.class);
        }

        Subject subject = SecurityUtils.getSubject();
        //使用shiro验证
        log.info("authenticated:" + subject.isAuthenticated());
        log.info("isRemembered" + subject.isRemembered());
        if (!subject.isAuthenticated() && !subject.isRemembered()) {
            log.info("未登录用户尝试访问需要登录的接口");
            return false;
        }

        String requestAPI = getPathWithinApplication(request);

        return true;
    }
}
