package com.number47.white.blog.config;

import com.number47.white.blog.constant.ShiroConstant;
import com.number47.white.blog.exception.CustomModularRealmAuthenticator;
import com.number47.white.blog.filter.AuthFilter;
import com.number47.white.blog.filter.ExceptionFilter;
import com.number47.white.blog.shiro.WBRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
* @Description:    Shiro配置
* @Author:         number47
* @CreateDate:      2020-08-13 14:21
*/
@Configuration
public class ShiroConfiguration {

    @Bean
    public static LifecycleBeanPostProcessor getLifecycleBeanProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public ModularRealmAuthenticator authenticator(){
        ModularRealmAuthenticator authenticator = new CustomModularRealmAuthenticator();
        return authenticator;
    }

    /**
     * 安全管理器
     * @Author number47
     * @CreateTime 2019/6/12 10:34
     */
    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 自定义Realm验证
        securityManager.setRealm(getWJRealm());
        //记住我
        securityManager.setRememberMeManager(rememberMeManager());
        /*
         * 关闭shiro自带的session，详情见文档
         * http://shiro.apache.org/session-management.html#SessionManagement-StatelessApplications%28Sessionless%29
         */
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        securityManager.setSubjectDAO(subjectDAO);
        return securityManager;
    }

    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        // 添加自己的过滤器并取名jwt
        Map<String, Filter> filter = new HashMap<>();
        filter.put("jwt",new AuthFilter());
        shiroFilterFactoryBean.setFilters(filter);
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //配置不会被拦截的链接
        LinkedHashMap<String, String> filterRuleMap = new LinkedHashMap<>();
        // 登录接口 不通过jwt
        filterRuleMap.put(ShiroConstant.LOGIN_URL,"anon");
        //所有请求通过我们自己的JwtFilter
        filterRuleMap.put("/**","jwt");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterRuleMap);
        return shiroFilterFactoryBean;
    }

    /**
     * 身份验证器
     * @Author number47
     * @CreateTime 2019/6/12 10:37
     */
    @Bean
    public WBRealm getWJRealm() {
        WBRealm wbRealm = new WBRealm();
//        wbRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return wbRealm;
    }
    /**
     * 凭证匹配器
     * 将密码校验交给Shiro的SimpleAuthenticationInfo进行处理,在这里做匹配配置
     * @Author number47
     * @CreateTime 2019/6/12 10:48
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        //指定加密方式为MD5
        hashedCredentialsMatcher.setHashAlgorithmName("md5");
        //加密次数
        hashedCredentialsMatcher.setHashIterations(2);
        return hashedCredentialsMatcher;
    }
    /**
     * 开启Shiro-aop注解支持
     * @Attention 使用代理方式所以需要开启代码支持
     * @Author number47
     * @CreateTime 2019/6/12 8:38
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    /**
     * cookie管理对象
     * @return
     */
    public CookieRememberMeManager rememberMeManager(){
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        // rememberMe cookie加密的密钥
        cookieRememberMeManager.setCipherKey("number47".getBytes());
        return cookieRememberMeManager;
    }
    /**
     * cookie对象
     * @return
     */
    @Bean
    public SimpleCookie rememberMeCookie(){
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        // 设置cookie的过期时间，单位为秒，记住我时长 30天
        simpleCookie.setMaxAge(259200);
        return simpleCookie;
    }

    @Bean
    public FilterRegistrationBean<ExceptionFilter> exceptionFilterRegistration() {
        FilterRegistrationBean<ExceptionFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new ExceptionFilter());
        registration.setName("exceptionFilter");
        /* 这个序号要很小，保证 exceptionFilter 是所有过滤器链的入口 */
        registration.setOrder(Integer.MIN_VALUE);
        return registration;
    }


}


