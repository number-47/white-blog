package com.number47.white.blog.system.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.number47.white.blog.constant.CommonConstant;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 菜单
 * </p>
 *
 * @author number47
 * @since 2020-09-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="AdminMenuDto对象", description="菜单")
//null的属性不显示
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AdminMenuDto {

    @ApiModelProperty(value = "与Vue路由中的path对应，及地址路径")
    private String path;

    @ApiModelProperty(value = "与Vue路由中的name属性对应")
    private String name;

    @ApiModelProperty(value = "菜单名称，用于渲染导航栏（菜单名）界面")
    private String nameZh;

    @ApiModelProperty(value = "图标")
    private String icon;

    @ApiModelProperty(value = "组件名，用于解析路由对应组件")
    private String component;

    @ApiModelProperty(value = "父节点id")
    private Long parentId;

    @ApiModelProperty(value = "排序")
    private Integer sequence;

    @ApiModelProperty(value = "重定向")
    private String redirect;

    @ApiModelProperty(value = "是否隐藏")
    private boolean hidden;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "菜单元信息")
    private Meta meta;

    @ApiModelProperty(value = "子菜单")
    List<AdminMenuDto> children;

    @JsonIgnore
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ApiModelProperty(value = "页码")
    private int pageNum = CommonConstant.PAGE_NUM;

    @JsonIgnore
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ApiModelProperty(value = "每页数量")
    private int pageSize = CommonConstant.PAGE_SIZE;
}
