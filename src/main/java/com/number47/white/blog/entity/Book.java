package com.number47.white.blog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 图书
 * </p>
 *
 * @author number47
 * @since 2020-08-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Book对象", description="图书")
public class Book implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "封面链接")
    private String cover;

    @ApiModelProperty(value = "书名")
    private String title;

    @ApiModelProperty(value = "作者")
    private String author;

    @ApiModelProperty(value = "出版日期")
    private String date;

    @ApiModelProperty(value = "出版社")
    private String press;

    @ApiModelProperty(value = "简介")
    private String abs;

    @ApiModelProperty(value = "类别id")
    private Long cid;

    @ApiModelProperty(value = "所属分类")
    @TableField(exist = false)
    private Category category;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;
}
