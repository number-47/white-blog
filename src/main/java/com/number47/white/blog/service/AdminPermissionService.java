package com.number47.white.blog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.number47.white.blog.dto.AdminPermissionDto;
import com.number47.white.blog.entity.AdminPermission;

import java.util.List;
/**
 * <p>
 * 权限/菜单 服务类
 * </p>
 *
 * @author number47
 * @since 2020-09-14
 */
public interface AdminPermissionService extends IService<AdminPermission> {
   List<AdminPermission> listAllAdminPermission(AdminPermissionDto adminPermissionDto);

   int createAdminPermission(AdminPermissionDto adminPermissionDto);

   int updateAdminPermission(Long id, AdminPermissionDto adminPermissionDto);

   int deleteAdminPermission(Long id);

    IPage<AdminPermission> listAdminPermission(Page<AdminPermission> page, AdminPermissionDto adminPermissionDto);

    AdminPermission getAdminPermission(Long id);
}
