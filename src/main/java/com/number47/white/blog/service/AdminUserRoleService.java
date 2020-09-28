package com.number47.white.blog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.number47.white.blog.dto.AdminUserRoleDto;
import com.number47.white.blog.entity.AdminUserRole;

import java.util.List;
/**
 * <p>
 * 后台用户角色关联表 服务类
 * </p>
 *
 * @author number47
 * @since 2020-09-14
 */
public interface AdminUserRoleService extends IService<AdminUserRole> {
   List<AdminUserRole> listAllAdminUserRole(AdminUserRoleDto adminUserRoleDto);

   int createAdminUserRole(AdminUserRoleDto adminUserRoleDto);

   int updateAdminUserRole(Long id, AdminUserRoleDto adminUserRoleDto);

   int deleteAdminUserRole(Long id);

    IPage<AdminUserRole> listAdminUserRole(Page<AdminUserRole> page, AdminUserRoleDto adminUserRoleDto);

    AdminUserRole getAdminUserRole(Long id);

    List<Long> listRoleIdsByUid(Long id);
}
