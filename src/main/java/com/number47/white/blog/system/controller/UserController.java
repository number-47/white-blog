package com.number47.white.blog.system.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.number47.white.blog.common.CommonPage;
import com.number47.white.blog.common.CommonResult;
import com.number47.white.blog.common.PageParam;
import com.number47.white.blog.config.PasswordConfig;
import com.number47.white.blog.constant.CommonConstant;
import com.number47.white.blog.exception.FailRequestException;
import com.number47.white.blog.exception.SystemErrorException;
import com.number47.white.blog.system.dto.UserDto;
import com.number47.white.blog.system.entity.User;
import com.number47.white.blog.system.service.AdminUserRoleService;
import com.number47.white.blog.system.service.UserService;
import com.number47.white.blog.util.JwtUtil;
import com.number47.white.blog.util.ShiroUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author number47
 * @since 2020-08-11
 */
@RestController
@Api(tags = "UserController", description = "用户有关接口")
@RequestMapping("/api/user")
@Log4j2
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private AdminUserRoleService adminUserRoleService;
	@Autowired
	private PasswordConfig passwordConfig;

	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	/**
	 * @Description: 获取全部列表
	 * @Param: [userDto]
	 * @return:
	 * @Author: number47
	 * @Date: 2020-08-11
	 */
	@ApiModelProperty(value = "获取全部列表")
	@RequestMapping(value = "listAll", method = RequestMethod.GET)
	@ResponseBody
	public CommonResult<List<User>> getUserList(UserDto userDto) {
		return CommonResult.success(userService.listAllUser(userDto));
	}

	/**
	 * @Description: 添加
	 * @Param: [user]
	 * @return:
	 * @Author: number47
	 * @Date: 2020-08-11
	 */
	@ApiModelProperty(value = "添加")
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	@ResponseBody
	public CommonResult createUser(@Validated @RequestBody UserDto userDto, BindingResult result) {
		if (result.hasErrors()) {
			return CommonResult.validateFailed(result.getFieldError().getDefaultMessage());
		}
		CommonResult commonResult;
		checkUserName(userDto.getUsername(),CommonConstant.OPERATION_CREATE,null);
		// 设置默认密码
		String password = passwordConfig.getDefaultPassword();
		// 生成盐,默认长度 16 位
		String salt = new SecureRandomNumberGenerator().nextBytes().toString();
		// 设置 hash 算法迭代次数
		int times = 2;
		// 得到 hash 后的密码
		String encodedPassword = new SimpleHash("md5", password, salt, times).toString();
		// 存储用户信息，包括 salt 与 hash 后的密码
		userDto.setPassword(encodedPassword);
		userDto.setSalt(salt);
		int count = userService.createUser(userDto);
		if (userDto.getRoles() != null && userDto.getRoles().size() > 0){
			adminUserRoleService.createBathAdminUserRole(userDto.getRoles(),userDto.getId());
		}
		if (count == 1) {
			userDto.setDefaultPassword(password);
			commonResult = CommonResult.success(userDto);
			LOGGER.debug("create User success:{}", userDto);
		} else {
			LOGGER.error("create User failed:{}", userDto);
			throw new SystemErrorException("操作失败，请联系管理员");
		}
		return commonResult;
	}

	/**
	 * @Description: 通过id更新
	 * @Param: [id, user, result]
	 * @return:
	 * @Author: number47
	 * @Date: 2020-08-11
	 */
	@ApiModelProperty(value = "通过id更新")
	@RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
	@ResponseBody
	public CommonResult updateUser(@PathVariable("id") Long id, @Validated @RequestBody UserDto userDto, BindingResult result) {
		if (result.hasErrors()) {
			return CommonResult.validateFailed(result.getFieldError().getDefaultMessage());
		}
		CommonResult commonResult;
		checkUserName(userDto.getUsername(),CommonConstant.OPERATION_UPDATE,id.toString());
		if (userDto.getRoles() != null && userDto.getRoles().size() > 0){
			adminUserRoleService.createBathAdminUserRole(userDto.getRoles(),userDto.getId());
		}
		int count = userService.updateUser(id, userDto);
		if (count == 1) {
			commonResult = CommonResult.success(userDto);
			LOGGER.debug("update user success:{}", userDto);
		} else {
			LOGGER.error("update user failed:{}", userDto);
			throw new SystemErrorException("操作失败，请联系管理员");
		}
		return commonResult;
	}

	/**
	 * @Description: 通过id删除
	 * @Param: [id]
	 * @return:
	 * @Author: number47
	 * @Date: 2020-08-11
	 */
	@ApiModelProperty(value = "通过id删除")
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public CommonResult deleteUser(@PathVariable("id") Long id) {
		int count = userService.deleteUser(id);
		if (count == 1) {
			LOGGER.debug("delete User success :id={}", id);
			return CommonResult.success(null);
		} else {
			LOGGER.error("delete User failed :id={}", id);
			throw new SystemErrorException("操作失败，请联系管理员");
		}
	}

	/**
	 * @Description: 分页获取列表
	 * @Param: [pageNum, pageSize, userDto]
	 * @return:
	 * @Author: number47
	 * @Date: 2020-08-11
	 */
	@ApiModelProperty(value = "分页获取列表")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public CommonResult<CommonPage<UserDto>> listUser(UserDto userDto, PageParam pageParam) {
		Page<User> page = new Page<>(pageParam.getPageNum(), pageParam.getPageSize());
		IPage<User> userPage = userService.listUser(page,userDto);
		// user转化UserDto
		List<UserDto> userDtoList = handleUserListToUserDtoList(userPage.getRecords());
		// 返回UserDto的page
		return CommonResult.success(CommonPage.replacePageList(userPage,userDtoList));
	}

	/**
	 * @Description: 通过id获取
	 * @Param: [id]
	 * @return:
	 * @Author: number47
	 * @Date: 2020-08-11
	 */
	@ApiModelProperty(value = "通过id获取")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public CommonResult<User> getUser(@PathVariable("id") Long id) {
		return CommonResult.success(userService.getUser(id));
	}

	/**
	 * @Description: 登录
	 * @Param: [id]
	 * @return:
	 * @Author: number47
	 * @Date: 2020-08-11
	 */
	@ApiModelProperty(value = "登录")
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public CommonResult<Object> getUser(@RequestBody UserDto userDto) {
		String message;
		HashMap<String, Object> result = new HashMap<>(2);
		String username = userDto.getUsername();
		String password = userDto.getPassword();
		User userBean = userService.getUserByName(username);
		if (userBean == null) {
			log.error("用户不存在!)");
			message = "用户不存在";
			return CommonResult.failed(message);
		}
		String passwordInDB = userBean.getPassword();
		String salt = userBean.getSalt();
		byte[]  passwordInDBByte= passwordInDB.getBytes();
		// 根据salt生成加密密码
		byte[] passwordByte = new SimpleHash("md5", password, salt, 2).toString().getBytes();
		boolean verifyPassword =  MessageDigest.isEqual(passwordByte, passwordInDBByte);
		if (verifyPassword == false){
			log.error("密码错误!");
			message = "密码错误";
			return CommonResult.failed(message);
		}
		if (userBean.getEnabled() == false) {
			log.error("账号已被锁定,请联系管理员！");
			message = "账号已被锁定,请联系管理员！";
			return CommonResult.failed(message);
		}
		//返回的token
		String token =  JwtUtil.sign(username, passwordInDB);
		String refreshToken =  JwtUtil.generalRefreshToken(username);
		result.put("token", token);
		result.put("expireTime", JwtUtil.getExp(token));
		result.put("refreshToken", refreshToken);
		result.put("refreshExpireTime", JwtUtil.getExp(refreshToken));
		result.put("username", username);
		return CommonResult.success(result);
	}

	@ApiModelProperty(value = "注册")
	@PostMapping("/register")
	@ResponseBody
	public CommonResult<String> register(@RequestBody UserDto user) {
		return CommonResult.success(userService.register(user));
	}

	@ApiModelProperty(value = "登出")
	@GetMapping("/logout")
	@ResponseBody
	public CommonResult<String> logout() {
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		String message = "成功登出";
		return CommonResult.success(message);
	}

	/**
	 * @Description: 用户信息
	 * @Param: [token]
	 * @return:
	 * @Author: number47
	 * @Date: 2020-08-11
	 */
	@ApiModelProperty(value = "用户信息")
	@RequestMapping(value = "/info", method = RequestMethod.GET)
	@ResponseBody
	public CommonResult<UserDto> getUser(@RequestParam(name = "token") String token) {
		String userName = JwtUtil.getUsername(token);
		User user = userService.getUserByName(userName);
		UserDto userDto = new UserDto();
		userDto.setId(String.valueOf(user.getId()));
		BeanUtils.copyProperties(user, userDto);
		return CommonResult.success(userDto);
	}

	/**
	 * @Description: 刷新token
	 * @Param: [token]
	 * @return:
	 * @Author: number47
	 * @Date: 2020-08-11
	 */
	@ApiModelProperty(value = "刷新token")
	@RequestMapping(value = "/refreshToken", method = RequestMethod.POST)
	@ResponseBody
	public CommonResult<HashMap<String,Object>> refreshToken(@RequestBody HashMap<String,String> data) {
		String userName = data.get("userName");
		String refreshToken = data.get("refreshToken");
		HashMap<String,Object> result = new HashMap<>();
		// 校验refreshToken
		if (!JwtUtil.verify(refreshToken,userName,"number47")){
			throw new AuthenticationException("refreshToken无效");
		}
		User userBean = userService.getUserByName(userName);
		String passwordInDB = userBean.getPassword();
		// 重新生成token
		String newToken =  JwtUtil.sign(userName, passwordInDB);
		result.put("token",newToken);
		result.put("expireTime",JwtUtil.getExp(newToken));
		return CommonResult.success(result);
	}



	/**
	 * User装UserDto
	 * @param user
	 * @return
	 */
	private UserDto handleUserToUserDto(User user){
		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(user,userDto);
		List<String> roleIds = new ArrayList<>();
		List<Long> roleIdsByUid = adminUserRoleService.listRoleIdsByUid(user.getId());
		roleIdsByUid.stream().forEach(rid -> roleIds.add(rid.toString()));
		userDto.setRoles(roleIds);
		userDto.setId(user.getId().toString());
		return userDto;
	}

	/**
	 * UserList转UserDtoList
	 * @param users
	 * @return
	 */
	private List<UserDto> handleUserListToUserDtoList(List<User> users){
		ArrayList<UserDto> userDtos = new ArrayList<>();
		for (User user:users) {
			userDtos.add(handleUserToUserDto(user));
		}
		return userDtos;
	}

	/**
	 * 检查是否存在用户名
	 * @param userName 用户名
	 * @param operate
	 */
	private void checkUserName(String userName,String operate, String userId){
		User user = userService.getUserByName(userName);
		if (CommonConstant.OPERATION_CREATE.equals(operate)){
			if (user != null){
				throw new FailRequestException("用户名已存在");
			}
		}else if (CommonConstant.OPERATION_UPDATE.equals(operate)){
			if (user != null && StrUtil.isNotBlank(userId) && !userId.equals(user.getId().toString())){
				throw new FailRequestException("用户名已存在");
			}
		}
	}


}
