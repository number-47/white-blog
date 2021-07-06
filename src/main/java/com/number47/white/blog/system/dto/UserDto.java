package com.number47.white.blog.system.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.number47.white.blog.annotation.DateTime;
import com.number47.white.blog.annotation.Phone;
import com.number47.white.blog.config.PasswordConfig;
import com.number47.white.blog.system.entity.AdminRole;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author number47
 * @since 2020-08-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="UserDto对象", description="用户表")
//null的属性不显示
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto implements Serializable {


    private static final long serialVersionUID = 1380700475070348205L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @NotBlank(message = "登录名不允许为空")
    @Size(min = 2,max = 15,message = "登录名长度必须在{min}到{max}之间")
    @ApiModelProperty(value = "登录名")
    private String username;

    @Size(min = 6,max = 15,message = "密码长度必须在{min}到{max}之间")
    @JsonIgnore
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ApiModelProperty(value = "密码")
    private String password;

    @JsonIgnore
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ApiModelProperty(value = "盐")
    private String salt;

    @Size(max = 15,message = "昵称长度必须在{min}到{max}")
    @ApiModelProperty(value = "昵称")
    private String name;

    @Phone
    @ApiModelProperty(value = "手机号")
    private String phone;

    @Email(message = "邮箱格式不正确")
    @ApiModelProperty(value = "邮箱")
    private String email;

    @NotBlank(message = "用户状态不能为空")
    @ApiModelProperty(value = "是否启用 1：启用 0：禁用")
    private Boolean enabled;

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

    @ApiModelProperty(value = "角色列表")
    private List<String> roles;

    @ApiModelProperty(value = "角色列表")
    private List<AdminRole> adminRoles;

    @ApiModelProperty(value = "权限指令")
    @TableField(exist = false)
    private List<String> permissionDirects;

    @ApiModelProperty(value = "默认密码")
    @TableField(exist = false)
    private String defaultPassword;

}
