package com.number47.white.blog.system.dto;

import java.time.LocalDateTime;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.number47.white.blog.constant.CommonConstant;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 权限/菜单
 * </p>
 *
 * @author number47
 * @since 2020-09-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="AdminPermissionDto对象", description="权限/菜单")
public class AdminPermissionDto {

    @ApiModelProperty(value = "权限/菜单名")
    private String name;

    @ApiModelProperty(value = "描述")
    private String desc;

    @ApiModelProperty(value = "地址")
    private String url;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "页码")
    private int pageNum = CommonConstant.PAGE_NUM;
    @ApiModelProperty(value = "每页数量")
    private int pageSize = CommonConstant.PAGE_SIZE;
}
