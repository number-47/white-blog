package com.number47.white.blog.system.dto;

import com.number47.white.blog.system.entity.AdminRole;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @program: white-blog
 * @description: 菜单的Meta
 * @author: number47
 * @create: 2020-09-29 09:51
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Meta对象", description="菜单Meta")
public class Meta {
    /**
     * 菜单名称
     */
    private String title;
    /**
     * 图标
     */
    private String icon;
    /**
     * 哪些角色可以看到这个菜单
     */
    private List<AdminRole> roles;
}
