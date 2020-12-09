package com.number47.white.blog.filter;

import com.number47.white.blog.common.CommonResult;
import com.number47.white.blog.system.dto.UserDto;
import com.number47.white.blog.system.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @program: white-blog
 * @description: 捕获Filter的异常
 * @author: number47
 * @create: 2020-12-08 13:41
 **/
@RestController
@Api(tags = "ErrorController", description = "捕获Filter的异常")
@RequestMapping("/error")
public class ErrorController {

    @Resource
    private HttpServletRequest request;

    @ApiModelProperty(value = "捕获Filter的异常")
    @RequestMapping(value = "exthrow", method = RequestMethod.GET)
    public void exthrow() throws Exception {
        throw ((Exception) request.getAttribute("filter.error"));
    }
}
