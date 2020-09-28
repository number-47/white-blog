package com.number47.white.blog.common;

/**
 * @program: mall
 * @description: 枚举了一些常用API操作码
 * @author: number47
 * @create: 2020-08-05 14:07
 **/
public enum ResultCode implements IErrorCode {
    SUCCESS(20000, "操作成功"),
    FAILED(50000, "操作失败"),
    VALIDATE_FAILED(40004, "参数检验失败"),
    UNAUTHORIZED(50014, "token已经过期"),
    ILLEGAL_TOKEN(50008, "无效的token"),
    OTHER_CLIENTS_LOGIN(50012, "账户在其他地方登录"),
    FORBIDDEN(40003, "没有相关权限");
    private long code;
    private String message;

    private ResultCode(long code, String message) {
        this.code = code;
        this.message = message;
    }

    public long getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
