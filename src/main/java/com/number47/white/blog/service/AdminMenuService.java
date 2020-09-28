package com.number47.white.blog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.number47.white.blog.entity.AdminMenu;
import com.number47.white.blog.dto.AdminMenuDto;

import java.util.List;
/**
 * <p>
 * 菜单 服务类
 * </p>
 *
 * @author number47
 * @since 2020-09-14
 */
public interface AdminMenuService extends IService<AdminMenu> {
   List<AdminMenu> listAllAdminMenu(AdminMenuDto adminMenuDto);

   int createAdminMenu(AdminMenuDto adminMenuDto);

   int updateAdminMenu(Long id, AdminMenuDto adminMenuDto);

   int deleteAdminMenu(Long id);

    IPage<AdminMenu> listAdminMenu(Page<AdminMenu> page, AdminMenuDto adminMenuDto);

    AdminMenu getAdminMenu(Long id);

    List<AdminMenu> getMenusByCurrentUser();

}
