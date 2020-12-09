package com.number47.white.blog.exception;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.pam.AuthenticationStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.realm.Realm;

import java.util.Collection;

/**
 * @program: white-blog
 * @description: 自定义重写ModularRealmAuthenticator类，用于处理多realm的自定义捕获异常问题
 * @author: number47
 * @create: 2020-12-07 16:13
 **/
@Slf4j
public class CustomModularRealmAuthenticator extends ModularRealmAuthenticator {
    /**
     * 重写多realm校验方法，避免无法捕获到realm中的自定义异常
     * @param realms
     * @param token
     * @return
     */
    @Override
    protected AuthenticationInfo doMultiRealmAuthentication(Collection<Realm> realms, AuthenticationToken token) {
        AuthenticationStrategy strategy = getAuthenticationStrategy();
        AuthenticationInfo aggregate = strategy.beforeAllAttempts(realms,token);
        if (log.isTraceEnabled()){
            log.trace("Iterating through {} realms for PAM authentication",realms.size());
        }
        for (Realm realm:realms){
            aggregate = strategy.beforeAttempt(realm,token,aggregate);
            if (realm.supports(token)){
                log.trace("Attempting to authenticate token [{}] using realm [{}]",token,realm);
                AuthenticationInfo info = null;
                Throwable t = null;
                info = realm.getAuthenticationInfo(token);
                aggregate = strategy.afterAttempt(realm,token,info,aggregate,t);
            }else {
                log.debug("Realm [{}] does not support token {}. Skipping realm.",realm, token);
            }
        }
        aggregate = strategy.afterAllAttempts(token,aggregate);
        return aggregate;
    }
}
