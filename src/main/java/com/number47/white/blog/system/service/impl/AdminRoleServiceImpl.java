package com.number47.white.blog.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.number47.white.blog.system.dao.AdminRoleMapper;
import com.number47.white.blog.system.dto.AdminRoleDto;
import com.number47.white.blog.system.dto.AdminRoleMenuDto;
import com.number47.white.blog.system.dto.AdminUserRoleDto;
import com.number47.white.blog.system.entity.AdminRole;
import com.number47.white.blog.system.entity.AdminRoleMenu;
import com.number47.white.blog.system.entity.AdminUserRole;
import com.number47.white.blog.system.service.AdminRoleMenuService;
import com.number47.white.blog.system.service.AdminRoleService;
import com.number47.white.blog.system.service.AdminUserRoleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 后台角色 服务实现类
 * </p>
 *
 * @author number47
 * @since 2020-09-14
 */
@Service
public class AdminRoleServiceImpl extends ServiceImpl<AdminRoleMapper, AdminRole> implements AdminRoleService {
    @Autowired
    private AdminRoleMapper adminRoleMapper;
    @Autowired
    private AdminRoleMenuService adminRoleMenuService;
    @Autowired
    private AdminUserRoleService adminUserRoleService;

    @Override
    public List<AdminRole> listAllAdminRole(AdminRoleDto adminRoleDto) {
        AdminRole adminRole = new AdminRole();
        BeanUtils.copyProperties(adminRoleDto,adminRole);
        QueryWrapper<AdminRole> queryWrapper = new QueryWrapper<>(adminRole);
        return adminRoleMapper.selectList(queryWrapper);
    }

    @Override
    public int createAdminRole(AdminRoleDto adminRoleDto) {
        AdminRole adminRole = new AdminRole();
        BeanUtils.copyProperties(adminRoleDto,adminRole);
        adminRole.setId(Long.parseLong(adminRoleDto.getId()));
        return adminRoleMapper.insert(adminRole);
    }

    @Override
    public int updateAdminRole(Long id, AdminRoleDto adminRoleDto) {
         AdminRole adminRole = new AdminRole();
         BeanUtils.copyProperties(adminRoleDto,adminRole);
         adminRole.setId(id);
         return adminRoleMapper.updateById(adminRole);
    }

    @Override
    public int deleteAdminRole(Long id) {
         return adminRoleMapper.deleteById(id);
    }

    @Override
    public IPage<AdminRole> listAdminRole(Page<AdminRole> page, AdminRoleDto adminRoleDto) {
        AdminRole adminRole = new AdminRole();
        BeanUtils.copyProperties(adminRoleDto,adminRole);
        QueryWrapper<AdminRole> queryWrapper = new QueryWrapper<>(adminRole);
        return adminRoleMapper.selectPage(page,queryWrapper);
    }

    @Override
    public AdminRole getAdminRole(Long id) {
         return adminRoleMapper.selectById(id);
    }

    /**
     * 获取菜单有权限的角色
     * @param menusId 菜单id
     * @return
     */
    @Override
    public List<AdminRole> getAdminRolesByMenuId(Long menusId) {
        List<AdminRole> adminRoles = new ArrayList<>();
        AdminRoleMenuDto adminRoleMenuDto = new AdminRoleMenuDto();
        adminRoleMenuDto.setMid(menusId);
        List<AdminRoleMenu> adminRoleMenus = adminRoleMenuService.listAllAdminRoleMenu(adminRoleMenuDto);
        for (AdminRoleMenu adminRoleMenu:adminRoleMenus) {
            AdminRole adminRole = getAdminRole(adminRoleMenu.getRid());
            if (adminRole != null){
                adminRoles.add(adminRole);
            }
        }
        return adminRoles;
    }

    /**
     * 获取用户的角色
     * @param userId
     * @return
     */
    @Override
    public List<AdminRole> getAdminRolesByUserId(Long userId) {
        List<AdminRole> adminRoles = new ArrayList<>();
        AdminUserRoleDto adminUserRoleDto = new AdminUserRoleDto();
        adminUserRoleDto.setUid(userId);
        List<AdminUserRole> adminUserRoles = adminUserRoleService.listAllAdminUserRole(adminUserRoleDto);
        for (AdminUserRole adminUserRole:adminUserRoles) {
            AdminRole adminRole = getAdminRole(adminUserRole.getRid());
            if (adminRole != null){
                adminRoles.add(adminRole);
            }
        }
        return adminRoles;
    }

    @Override
    public AdminRole getRoleByName(String roleName) {
        AdminRole adminRole = new AdminRole();
        adminRole.setName(roleName);
        QueryWrapper<AdminRole> queryWrapper = new QueryWrapper<>(adminRole);
        return adminRoleMapper.selectOne(queryWrapper);
    }
}
