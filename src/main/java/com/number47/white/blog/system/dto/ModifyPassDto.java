package com.number47.white.blog.system.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author number47
 * @date 2021/7/6 16:59
 * @description 修改密码接收对象
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="修改密码接收对象", description="修改密码接收对象")
//null的属性不显示
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ModifyPassDto  implements Serializable {

	private static final long serialVersionUID = 4071125120574051182L;

	@ApiModelProperty(value = "id")
	private String id;

	@NotBlank(message = "登录名不允许为空")
	@Size(min = 2,max = 15,message = "登录名长度必须在{min}到{max}之间")
	@ApiModelProperty(value = "登录名")
	private String username;

	@Size(min = 6,max = 15,message = "旧密码长度必须在{min}到{max}之间")
	@NotBlank(message = "旧密码不允许为空")
	@JsonIgnore
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@ApiModelProperty(value = "旧密码")
	private String oldPass;

	@Size(min = 6,max = 15,message = "密码长度必须在{min}到{max}之间")
	@NotBlank(message = "密码不允许为空")
	@JsonIgnore
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@ApiModelProperty(value = "密码")
	private String pass;

	@Size(min = 6,max = 15,message = "确认密码长度必须在{min}到{max}之间")
	@JsonIgnore
	@NotBlank(message = "确认密码不允许为空")
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@ApiModelProperty(value = "确认密码是否一致")
	private String checkPass;
}
