package com.number47.white.blog.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value="AdminPermission对象", description="权限/菜单")
public class AdminPermission implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "权限/菜单id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

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

}
