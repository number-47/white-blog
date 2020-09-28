package com.number47.white.blog.common;

import lombok.Data;

/**
 * @program: mall
 * @description: 返回
 * @author: number47
 * @create: 2020-08-05 09:45
 **/
@Data
public class ResponseBean<T> {

    private String code;
    private String message;
    private T data;

}
