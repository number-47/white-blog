package com.number47.white.blog.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.number47.white.blog.common.CommonConstant;
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
public class AdminMenuDto {




    @ApiModelProperty(value = "与Vue路由中的path对应，及地址路径")
    private String path;

    @ApiModelProperty(value = "与Vue路由中的name属性对应")
    private String name;

    @ApiModelProperty(value = "中文名称，用于渲染导航栏（菜单名）界面")
    private String nameZh;

    @ApiModelProperty(value = "图标")
    private String iconCls;

    @ApiModelProperty(value = "组件名，用于解析路由对应组件")
    private String component;

    @ApiModelProperty(value = "父节点id")
    private Long parentId;

    @ApiModelProperty(value = "排序")
    private Integer sequence;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;


    @ApiModelProperty(value = "页码")
    private int pageNum = CommonConstant.PAGE_NUM;
    @ApiModelProperty(value = "每页数量")
    private int pageSize = CommonConstant.PAGE_SIZE;
}
