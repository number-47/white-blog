package com.number47.white.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.number47.white.blog.dao.AdminUserRoleMapper;
import com.number47.white.blog.dto.AdminUserRoleDto;
import com.number47.white.blog.entity.AdminUserRole;
import com.number47.white.blog.service.AdminUserRoleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 后台用户角色关联表 服务实现类
 * </p>
 *
 * @author number47
 * @since 2020-09-14
 */
@Service
public class AdminUserRoleServiceImpl extends ServiceImpl<AdminUserRoleMapper, AdminUserRole> implements AdminUserRoleService {
    @Autowired
    private AdminUserRoleMapper adminUserRoleMapper;

    @Override
    public List<AdminUserRole> listAllAdminUserRole(AdminUserRoleDto adminUserRoleDto) {
        AdminUserRole adminUserRole = new AdminUserRole();
        BeanUtils.copyProperties(adminUserRoleDto,adminUserRole);
        QueryWrapper<AdminUserRole> queryWrapper = new QueryWrapper<>(adminUserRole);
        return adminUserRoleMapper.selectList(queryWrapper);
    }

    @Override
    public int createAdminUserRole(AdminUserRoleDto adminUserRoleDto) {
        AdminUserRole adminUserRole = new AdminUserRole();
        BeanUtils.copyProperties(adminUserRoleDto,adminUserRole);
        return adminUserRoleMapper.insert(adminUserRole);
    }

    @Override
    public int updateAdminUserRole(Long id, AdminUserRoleDto adminUserRoleDto) {
         AdminUserRole adminUserRole = new AdminUserRole();
         BeanUtils.copyProperties(adminUserRoleDto,adminUserRole);
         adminUserRole.setId(id);
         return adminUserRoleMapper.updateById(adminUserRole);
    }

    @Override
    public int deleteAdminUserRole(Long id) {
         return adminUserRoleMapper.deleteById(id);
    }

    @Override
    public IPage<AdminUserRole> listAdminUserRole(Page<AdminUserRole> page, AdminUserRoleDto adminUserRoleDto) {
        AdminUserRole adminUserRole = new AdminUserRole();
        BeanUtils.copyProperties(adminUserRoleDto,adminUserRole);
        QueryWrapper<AdminUserRole> queryWrapper = new QueryWrapper<>(adminUserRole);
        return adminUserRoleMapper.selectPage(page,queryWrapper);
    }

    @Override
    public AdminUserRole getAdminUserRole(Long id) {
         return adminUserRoleMapper.selectById(id);
    }

    /**
     * 通过用户id查询所拥有的角色
     * @param userId 用户id
     * @return
     */
    @Override
    public List<Long> listRoleIdsByUid(Long userId) {
        AdminUserRole adminUserRole = new AdminUserRole();
        adminUserRole.setUid(userId);
        QueryWrapper<AdminUserRole> queryWrapper = new QueryWrapper<>(adminUserRole);
        List<AdminUserRole> roles = adminUserRoleMapper.selectList(queryWrapper);
        List<Long> ids = roles.stream().map(AdminUserRole::getUid).collect(Collectors.toList());
        return ids;
    }
}
