package com.number47.white.blog.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: white-blog
 * @description: 分页参数
 * @author: number47
 * @create: 2020-08-12 10:18
 **/
@Data
public class PageParam {
    @ApiModelProperty(value = "页码")
    private int pageNum = CommonConstant.PAGE_NUM;
    @ApiModelProperty(value = "每页数量")
    private int pageSize = CommonConstant.PAGE_SIZE;
}
