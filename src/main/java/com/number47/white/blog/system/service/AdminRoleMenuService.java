package com.number47.white.blog.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.number47.white.blog.system.dto.AdminRoleMenuDto;
import com.number47.white.blog.system.entity.AdminRoleMenu;

import java.util.List;
/**
 * <p>
 * 角色菜单关联关系 服务类
 * </p>
 *
 * @author number47
 * @since 2020-09-14
 */
public interface AdminRoleMenuService extends IService<AdminRoleMenu> {
   List<AdminRoleMenu> listAllAdminRoleMenu(AdminRoleMenuDto adminRoleMenuDto);

   int createAdminRoleMenu(AdminRoleMenuDto adminRoleMenuDto);

   int updateAdminRoleMenu(Long id, AdminRoleMenuDto adminRoleMenuDto);

   int deleteAdminRoleMenu(Long id);

    IPage<AdminRoleMenu> listAdminRoleMenu(Page<AdminRoleMenu> page, AdminRoleMenuDto adminRoleMenuDto);

    AdminRoleMenu getAdminRoleMenu(Long id);
}
