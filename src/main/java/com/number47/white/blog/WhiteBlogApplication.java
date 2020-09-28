package com.number47.white.blog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.number47.white.blog.dao")
public class WhiteBlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(WhiteBlogApplication.class, args);
    }

}
