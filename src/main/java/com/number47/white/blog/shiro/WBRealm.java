package com.number47.white.blog.shiro;

import com.number47.white.blog.common.JwtToken;
import com.number47.white.blog.common.ResultCode;
import com.number47.white.blog.exception.UnAuthorizedException;
import com.number47.white.blog.system.entity.AdminMenu;
import com.number47.white.blog.system.entity.AdminRole;
import com.number47.white.blog.system.entity.User;
import com.number47.white.blog.system.service.AdminMenuService;
import com.number47.white.blog.system.service.AdminRoleService;
import com.number47.white.blog.system.service.UserService;
import com.number47.white.blog.util.JwtUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @program: white-blog
 * @description:
 * @author: number47
 * @create: 2020-08-13 14:21
 **/
@Log4j2
public class WBRealm extends AuthorizingRealm {

	@Autowired
	private UserService userService;

	@Autowired
	private AdminRoleService adminRoleService;

	@Autowired
	private AdminMenuService adminMenuService;


	/**
	 * 必须重写此方法，不然Shiro会报错
	 * @param token
	 * @return
	 */
	@Override
	public boolean supports(AuthenticationToken token) {
		return token instanceof JwtToken;
	}


	/**
	 * 授权权限
	 *  用户进行权限验证时候Shiro会去缓存中找,如果查不到数据,会执行这个方法去查权限,并放入缓存中
	 * @param principalCollection
	 * @return
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		User sysUserEntity = (User) principalCollection.getPrimaryPrincipal();
		//获取用户ID
		Long userId =sysUserEntity.getId();
		//这里可以进行授权和处理
		Set<String> rolesSet = new HashSet<>();
		Set<String> permsSet = new HashSet<>();
		//查询角色和权限(这里根据业务自行查询)
		List<AdminRole> sysRoleEntityList = adminRoleService.getAdminRolesByUserId(userId);
		for (AdminRole sysRoleEntity:sysRoleEntityList) {
			rolesSet.add(sysRoleEntity.getName());
			List<AdminMenu> sysMenuEntityList = adminMenuService.listAdminMenuByRoleId(sysRoleEntity.getId());
			for (AdminMenu sysMenuEntity :sysMenuEntityList) {
				permsSet.add(sysMenuEntity.getName());
			}
		}
		//将查到的权限和角色分别传入authorizationInfo中
		authorizationInfo.setStringPermissions(permsSet);
		authorizationInfo.setRoles(rolesSet);
		return authorizationInfo;
	}

	/**
	 * 登录认证，获取认证信息，即根据 auth 中的用户名从数据库中获取密码、盐等并返回
	 *
	 * @param auth
	 * @return
	 * @throws AuthenticationException
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
		// 这里的 token是从 AuthFilter 的 executeLogin 方法传递过来的，已经经过了解密
		String token = (String) auth.getCredentials();
		// 解密获得username，用于和数据库进行对比
		String username = null;
		try {
			//这里工具类没有处理空指针等异常这里处理一下(这里处理科学一些)
			username = JwtUtil.getUsername(token);
		} catch (Exception e) {
			log.error("根据Token无法获取用户名");
			throw new UnAuthorizedException(ResultCode.ILLEGAL_TOKEN.getMessage());
		}
		if (username == null) {
			log.error("token无效(空''或者null都不行!)");
			throw new UnAuthorizedException(ResultCode.ILLEGAL_TOKEN.getMessage());
		}
		// 通过用户名查询用户
		User userBean = userService.getUserByName(username);
		String passwordInDB = userBean.getPassword();
		String salt = userBean.getSalt();
		if (userBean == null) {
			log.error("用户不存在!)");
			throw new UnAuthorizedException(ResultCode.ILLEGAL_TOKEN.getMessage());
		}
		if (JwtUtil.isTokenExpired(new Date(JwtUtil.getExp(token)))){
			log.error("token已过期");
			throw new UnAuthorizedException(ResultCode.UNAUTHORIZED.getMessage());
		}
		if (!JwtUtil.verify(token, username, userBean.getPassword())) {
			log.error("用户名或密码错误(token无效或者与登录者不匹配)!)");
			throw new UnAuthorizedException(ResultCode.ILLEGAL_TOKEN.getMessage());
		}
		if (userBean.getEnabled() == false) {
			log.error("账号已被锁定,请联系管理员！");
			throw new LockedAccountException("账号已被锁定,请联系管理员！");
		}
//		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(username, passwordInDB, ByteSource.Util.bytes(salt), getName());
		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(username, token, getName());
		return authenticationInfo;
	}
}
