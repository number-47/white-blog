package com.number47.white.blog.system.dto;

import java.time.LocalDateTime;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.number47.white.blog.constant.CommonConstant;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 后台角色
 * </p>
 *
 * @author number47
 * @since 2020-09-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="AdminRoleDto对象", description="后台角色")
public class AdminRoleDto {




    @ApiModelProperty(value = "角色en")
    private String name;

    @ApiModelProperty(value = "角色zh")
    private String nameZh;

    @ApiModelProperty(value = "是否可用 1：可用 0：禁止")
    private Boolean enabled;
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "页码")
    private int pageNum = CommonConstant.PAGE_NUM;
    @ApiModelProperty(value = "每页数量")
    private int pageSize = CommonConstant.PAGE_SIZE;
}
