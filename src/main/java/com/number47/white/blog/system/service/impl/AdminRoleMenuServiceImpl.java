package com.number47.white.blog.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.number47.white.blog.system.dao.AdminRoleMenuMapper;
import com.number47.white.blog.system.dto.AdminRoleMenuDto;
import com.number47.white.blog.system.entity.AdminRoleMenu;
import com.number47.white.blog.system.service.AdminRoleMenuService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 角色菜单关联关系 服务实现类
 * </p>
 *
 * @author number47
 * @since 2020-09-14
 */
@Service
public class AdminRoleMenuServiceImpl extends ServiceImpl<AdminRoleMenuMapper, AdminRoleMenu> implements AdminRoleMenuService {
    @Autowired
    private AdminRoleMenuMapper adminRoleMenuMapper;

    @Override
    public List<AdminRoleMenu> listAllAdminRoleMenu(AdminRoleMenuDto adminRoleMenuDto) {
        AdminRoleMenu adminRoleMenu = new AdminRoleMenu();
        BeanUtils.copyProperties(adminRoleMenuDto,adminRoleMenu);
        QueryWrapper<AdminRoleMenu> queryWrapper = new QueryWrapper<>(adminRoleMenu);
        return adminRoleMenuMapper.selectList(queryWrapper);
    }

    @Override
    public int createAdminRoleMenu(AdminRoleMenuDto adminRoleMenuDto) {
        AdminRoleMenu adminRoleMenu = new AdminRoleMenu();
        BeanUtils.copyProperties(adminRoleMenuDto,adminRoleMenu);
        return adminRoleMenuMapper.insert(adminRoleMenu);
    }

    @Override
    public int updateAdminRoleMenu(Long id, AdminRoleMenuDto adminRoleMenuDto) {
         AdminRoleMenu adminRoleMenu = new AdminRoleMenu();
         BeanUtils.copyProperties(adminRoleMenuDto,adminRoleMenu);
         adminRoleMenu.setId(id);
         return adminRoleMenuMapper.updateById(adminRoleMenu);
    }

    @Override
    public int deleteAdminRoleMenu(Long id) {
         return adminRoleMenuMapper.deleteById(id);
    }

    @Override
    public IPage<AdminRoleMenu> listAdminRoleMenu(Page<AdminRoleMenu> page, AdminRoleMenuDto adminRoleMenuDto) {
        AdminRoleMenu adminRoleMenu = new AdminRoleMenu();
        BeanUtils.copyProperties(adminRoleMenuDto,adminRoleMenu);
        QueryWrapper<AdminRoleMenu> queryWrapper = new QueryWrapper<>(adminRoleMenu);
        return adminRoleMenuMapper.selectPage(page,queryWrapper);
    }

    @Override
    public AdminRoleMenu getAdminRoleMenu(Long id) {
         return adminRoleMenuMapper.selectById(id);
    }
}
