package com.number47.white.blog.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.number47.white.blog.system.dto.AdminRoleDto;
import com.number47.white.blog.system.entity.AdminRole;

import java.util.List;
/**
 * <p>
 * 后台角色 服务类
 * </p>
 *
 * @author number47
 * @since 2020-09-14
 */
public interface AdminRoleService extends IService<AdminRole> {
   List<AdminRole> listAllAdminRole(AdminRoleDto adminRoleDto);

   int createAdminRole(AdminRoleDto adminRoleDto);

   int updateAdminRole(Long id, AdminRoleDto adminRoleDto);

   int deleteAdminRole(Long id);

    IPage<AdminRole> listAdminRole(Page<AdminRole> page, AdminRoleDto adminRoleDto);

    AdminRole getAdminRole(Long id);

    List<AdminRole> getAdminRolesByMenuId(Long menusId);

	List<AdminRole> getAdminRolesByUserId(Long userId);
}
