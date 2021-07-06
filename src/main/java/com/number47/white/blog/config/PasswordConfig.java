package com.number47.white.blog.config;

import io.swagger.models.auth.In;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author number47
 * @date 2021/7/6 08:46
 * @description 密码配置
 */
@Configuration
@Data
public class PasswordConfig {

	@Value("${password.defaultPassword}")
	private String defaultPassword;
	@Value("${password.isClear}")
	private Boolean isClear;
	@Value("${password.min}")
	private Integer min;
	@Value("${password.max}")
	private Integer max;


}
