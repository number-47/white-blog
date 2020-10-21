package com.number47.white.blog.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @program: white-blog
 * @description:
 * @author: number47
 * @create: 2020-09-14 11:09
 **/
public class SpringContextUtils implements ApplicationContextAware {

    private static ApplicationContext context;
    /**
     * Spring在bean初始化后会判断是不是ApplicationContextAware的子类
     * 如果该类是,setApplicationContext()方法,会将容器中ApplicationContext作为参数传入进去
     * @Author Sans
     * @CreateTime 2019/6/17 16:58
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextUtils.context = context;
    }
    /**
     * 通过Name返回指定的Bean
     * @Author Sans
     * @CreateTime 2019/6/17 16:03
     */
    public static <T> T getBean(Class<T> beanClass) {
        return context.getBean(beanClass);
    }

    public static ApplicationContext getContext(){
        return context;
    }
}
