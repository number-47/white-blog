package com.number47.white.blog.annotation;

import com.number47.white.blog.validator.DateTimeValidator;
import com.number47.white.blog.validator.PhoneValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description: 手机号校验注解
 * @Author: number47
 * @Date: 2020/10/26
 */
@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PhoneValidator.class)
public @interface Phone {

    String message() default "手机格式不正确";

    int length() default 11;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
