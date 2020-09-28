package com.number47.white.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.number47.white.blog.dao.AdminPermissionMapper;
import com.number47.white.blog.dto.AdminPermissionDto;
import com.number47.white.blog.entity.AdminPermission;
import com.number47.white.blog.service.AdminPermissionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 权限/菜单 服务实现类
 * </p>
 *
 * @author number47
 * @since 2020-09-14
 */
@Service
public class AdminPermissionServiceImpl extends ServiceImpl<AdminPermissionMapper, AdminPermission> implements AdminPermissionService {
    @Autowired
    private AdminPermissionMapper adminPermissionMapper;

    @Override
    public List<AdminPermission> listAllAdminPermission(AdminPermissionDto adminPermissionDto) {
        AdminPermission adminPermission = new AdminPermission();
        BeanUtils.copyProperties(adminPermissionDto,adminPermission);
        QueryWrapper<AdminPermission> queryWrapper = new QueryWrapper<>(adminPermission);
        return adminPermissionMapper.selectList(queryWrapper);
    }

    @Override
    public int createAdminPermission(AdminPermissionDto adminPermissionDto) {
        AdminPermission adminPermission = new AdminPermission();
        BeanUtils.copyProperties(adminPermissionDto,adminPermission);
        return adminPermissionMapper.insert(adminPermission);
    }

    @Override
    public int updateAdminPermission(Long id, AdminPermissionDto adminPermissionDto) {
         AdminPermission adminPermission = new AdminPermission();
         BeanUtils.copyProperties(adminPermissionDto,adminPermission);
         adminPermission.setId(id);
         return adminPermissionMapper.updateById(adminPermission);
    }

    @Override
    public int deleteAdminPermission(Long id) {
         return adminPermissionMapper.deleteById(id);
    }

    @Override
    public IPage<AdminPermission> listAdminPermission(Page<AdminPermission> page, AdminPermissionDto adminPermissionDto) {
        AdminPermission adminPermission = new AdminPermission();
        BeanUtils.copyProperties(adminPermissionDto,adminPermission);
        QueryWrapper<AdminPermission> queryWrapper = new QueryWrapper<>(adminPermission);
        return adminPermissionMapper.selectPage(page,queryWrapper);
    }

    @Override
    public AdminPermission getAdminPermission(Long id) {
         return adminPermissionMapper.selectById(id);
    }
}
