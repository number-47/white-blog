package com.number47.white.blog.validator;

import cn.hutool.core.util.StrUtil;
import com.number47.white.blog.annotation.DateTime;
import com.number47.white.blog.annotation.Phone;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * @program: white-blog
 * @description: 手机号校验
 * @author: number47
 * @create: 2020-10-26 14:16
 **/
public class PhoneValidator implements ConstraintValidator<Phone,String> {

    private Phone phone;

    @Override
    public void initialize(Phone constraintAnnotation) {
        this.phone=constraintAnnotation;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        // 如果value为空则不进行格式验证，为空的验证可以使用@NotBlank @NotNull @NotEmpty等注解
        if (StrUtil.isBlank(value)){
            return true;
        }
        if (value.length() != phone.length()){
            return false;
        }
        return true;
    }
}
