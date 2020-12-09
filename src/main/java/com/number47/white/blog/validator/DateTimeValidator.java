package com.number47.white.blog.validator;

import com.number47.white.blog.annotation.DateTime;
import lombok.extern.log4j.Log4j2;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @program: white-blog
 * @description: 邮箱校验
 * @author: number47
 * @create: 2020-10-26 14:16
 **/
@Log4j2
public class DateTimeValidator implements ConstraintValidator<DateTime, LocalDateTime> {

    private DateTime dateTime;

    @Override
    public void initialize(DateTime constraintAnnotation) {
        this.dateTime=constraintAnnotation;
    }

    @Override
    public boolean isValid(LocalDateTime value, ConstraintValidatorContext constraintValidatorContext) {
        // 如果value为空则不进行格式验证，为空的验证可以使用@NotBlank @NotNull @NotEmpty等注解
        if (value == null){
            return true;
        }
        String format = dateTime.format();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        try {
            dateTimeFormatter.format(value);
        }catch (IllegalArgumentException e){
            log.error("检验日期出错",e);
            return false;
        }
        return true;
    }
}
