package com.number47.white.blog.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.number47.white.blog.system.dao.AdminUserRoleMapper;
import com.number47.white.blog.system.dto.AdminUserRoleDto;
import com.number47.white.blog.system.entity.AdminUserRole;
import com.number47.white.blog.system.service.AdminUserRoleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
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
    @Autowired
    private AdminUserRoleService adminUserRoleService;

    @Override
    public List<AdminUserRole> listAllAdminUserRole(AdminUserRoleDto adminUserRoleDto) {
        AdminUserRole adminUserRole = new AdminUserRole();
        BeanUtils.copyProperties(adminUserRoleDto, adminUserRole);
        QueryWrapper<AdminUserRole> queryWrapper = new QueryWrapper<>(adminUserRole);
        return adminUserRoleMapper.selectList(queryWrapper);
    }

    @Override
    public int createAdminUserRole(AdminUserRoleDto adminUserRoleDto) {
        AdminUserRole adminUserRole = new AdminUserRole();
        BeanUtils.copyProperties(adminUserRoleDto, adminUserRole);
        adminUserRole.setId(Long.parseLong(adminUserRoleDto.getId()));
        return adminUserRoleMapper.insert(adminUserRole);
    }

    @Override
    public int updateAdminUserRole(Long id, AdminUserRoleDto adminUserRoleDto) {
        AdminUserRole adminUserRole = new AdminUserRole();
        BeanUtils.copyProperties(adminUserRoleDto, adminUserRole);
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
        BeanUtils.copyProperties(adminUserRoleDto, adminUserRole);
        QueryWrapper<AdminUserRole> queryWrapper = new QueryWrapper<>(adminUserRole);
        return adminUserRoleMapper.selectPage(page, queryWrapper);
    }

    @Override
    public AdminUserRole getAdminUserRole(Long id) {
        return adminUserRoleMapper.selectById(id);
    }

    /**
     * 通过用户id查询所拥有的角色
     *
     * @param userId 用户id
     * @return
     */
    @Override
    public List<Long> listRoleIdsByUid(Long userId) {
        AdminUserRole adminUserRole = new AdminUserRole();
        adminUserRole.setUid(userId);
        QueryWrapper<AdminUserRole> queryWrapper = new QueryWrapper<>(adminUserRole);
        List<AdminUserRole> roles = adminUserRoleMapper.selectList(queryWrapper);
        List<Long> ids = roles.stream().map(AdminUserRole::getRid).collect(Collectors.toList());
        return ids;
    }

    @Override
    public boolean createBathAdminUserRole(List<String> roleIds, String userId) {
        Long uId = Long.parseLong(userId);
        ArrayList<AdminUserRole> adminUserRoles = new ArrayList<>();
        HashMap<String, Object> map = new HashMap<>();
        map.put("uId", userId);
        adminUserRoleMapper.deleteByMap(map);
        for (String id : roleIds) {
            Long roleId = Long.parseLong(id);
            AdminUserRole adminUserRole = new AdminUserRole(uId, roleId);
            adminUserRoles.add(adminUserRole);
        }
        boolean result = adminUserRoleService.saveBatch(adminUserRoles);
        return result;
    }

    @Override
    public boolean createBathAdminUserRoleByUserIds(List<String> userIds, String roleId) {
        Long rId = Long.parseLong(roleId);
        ArrayList<AdminUserRole> adminUserRoles = new ArrayList<>();
        HashMap<String, Object> map = new HashMap<>();
        map.put("rId", roleId);
        adminUserRoleMapper.deleteByMap(map);
        List<Long> users = new ArrayList<>();
        // String 转Long
        userIds.stream().forEach(id -> users.add(Long.valueOf(id)));
        // 生成对象
        users.forEach(uid -> adminUserRoles.add(new AdminUserRole(uid, rId)));
        boolean result = adminUserRoleService.saveBatch(adminUserRoles);
        return result;
    }
}
