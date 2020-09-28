package com.number47.white.blog.controller;

import com.number47.white.blog.common.CommonResult;
import com.number47.white.blog.dto.AdminRoleDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: white-blog
 * @description:
 * @author: number47
 * @create: 2020-09-14 10:35
 **/
@RestController
@Api(tags = "AuthenticationController", description = "")
@RequestMapping("/api")
public class AuthenticationController {

    /**
     * @Description: 验证服务端登录状态
     * @Param: []
     * @return:
     * @Author: number47
     * @Date: 2020-08-11
     */
    @ApiModelProperty(value = "验证服务端登录状态")
    @RequestMapping(value = "authentication", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<String> getAdminRoleList(AdminRoleDto adminRoleDto) {
        return CommonResult.success("身份认证成功");
    }
}
