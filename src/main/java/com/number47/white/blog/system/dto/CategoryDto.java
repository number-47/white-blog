package com.number47.white.blog.system.dto;

import java.time.LocalDateTime;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.number47.white.blog.constant.CommonConstant;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 图书类别
 * </p>
 *
 * @author number47
 * @since 2020-08-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="CategoryDto对象", description="图书类别")
public class CategoryDto {



    private Long id;

    @ApiModelProperty(value = "分类名称")
    private String name;


    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "页码")
    private int pageNum = CommonConstant.PAGE_NUM;
    @ApiModelProperty(value = "每页数量")
    private int pageSize = CommonConstant.PAGE_SIZE;
}
