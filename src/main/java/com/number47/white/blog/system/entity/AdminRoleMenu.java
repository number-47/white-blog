package com.number47.white.blog.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * <p>
 * 角色菜单关联关系
 * </p>
 *
 * @author number47
 * @since 2020-09-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@ApiModel(value="AdminRoleMenu对象", description="角色菜单关联关系")
public class AdminRoleMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "角色id")
    private Long rid;

    @ApiModelProperty(value = "菜单id")
    private Long mid;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    public AdminRoleMenu(Long rid, Long mid) {
        this.rid = rid;
        this.mid = mid;
    }
}
