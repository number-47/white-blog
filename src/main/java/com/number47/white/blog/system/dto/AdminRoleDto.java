package com.number47.white.blog.system.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.number47.white.blog.annotation.DateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.number47.white.blog.constant.CommonConstant;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

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

    @ApiModelProperty(value = "角色Id")
    private String id;

    @ApiModelProperty(value = "角色en")
    private String name;

    @ApiModelProperty(value = "角色label")
    private String label;

    @ApiModelProperty(value = "角色zh")
    private String nameZh;

    @ApiModelProperty(value = "是否可用 1：可用 0：禁止")
    private Boolean enabled;

    @ApiModelProperty(value = "角色菜单权限")
    private List<String> menuIds;

    @DateTime
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @DateTime
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "页码")
    private int pageNum = CommonConstant.PAGE_NUM;
    @ApiModelProperty(value = "每页数量")
    private int pageSize = CommonConstant.PAGE_SIZE;
}
