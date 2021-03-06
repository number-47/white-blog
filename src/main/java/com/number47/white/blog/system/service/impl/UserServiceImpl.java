package com.number47.white.blog.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.number47.white.blog.system.dao.UserMapper;
import com.number47.white.blog.system.dto.UserDto;
import com.number47.white.blog.system.entity.AdminRole;
import com.number47.white.blog.system.entity.User;
import com.number47.white.blog.system.service.*;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author number47
 * @since 2020-08-11
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AdminUserRoleService adminUserRoleService;

    @Autowired
    private AdminRoleService adminRoleService;

    @Autowired
    private AdminMenuService adminMenuService;

    @Override
    public List<User> listAllUser(UserDto userDto) {
        User user = new User();
        BeanUtils.copyProperties(userDto, user);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>(user);
        return userMapper.selectList(queryWrapper);
    }

    @Override
    public int createUser(UserDto userDto) {
        User user = new User();
        BeanUtils.copyProperties(userDto, user);
        user.setId(Long.parseLong(userDto.getId()));
        return userMapper.insert(user);
    }

    @Override
    public int updateUser(Long id, UserDto userDto) {
        User user = new User();
        BeanUtils.copyProperties(userDto, user);
        user.setId(id);
        user.setUpdateTime(LocalDateTime.now());
        return userMapper.updateById(user);
    }

    @Override
    public int deleteUser(Long id) {
        return userMapper.deleteById(id);
    }

    @Override
    public IPage<User> listUser(Page<User> page, UserDto userDto) {
        User user = new User();
        BeanUtils.copyProperties(userDto, user);
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>(user);
        queryWrapper.orderByDesc(User::getCreateTime);
        return userMapper.selectPage(page, queryWrapper);
    }

    @Override
    public User getUser(Long id) {
        return userMapper.selectById(id);
    }

    @Override
    public User getUserByName(String userName) {
        //查询用户
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<User>(new User());
        lambdaQueryWrapper.eq(User::getUsername, userName);
        User user = userMapper.selectOne(lambdaQueryWrapper);
        List<String> roles = new ArrayList<>();
        List<AdminRole> adminRoles = new ArrayList<>();
        //查询用户角色,设置roles
        if (user != null) {
            List<Long> roleIds = adminUserRoleService.listRoleIdsByUid(user.getId());
            if (roleIds.size() > 0) {
                adminRoles = adminRoleService.listByIds(roleIds);
                //过滤无效的角色
                adminRoles = adminRoles.stream().filter(m -> m.getEnabled().equals(true))
                        .collect(Collectors.toList());
                adminRoles.stream().map(AdminRole::getId).forEach(m -> roles.add(m.toString()));
                user.setRoles(roles);
                user.setAdminRoles(adminRoles);
            }
        }
        // 查询权限命令
        if (user != null && CollectionUtil.isNotEmpty(adminRoles)) {
            List<Long> rIds = adminRoles.stream().map(AdminRole::getId).collect(Collectors.toList());
            List<String> permissionDirects = adminMenuService.listPermissionDirects(rIds, "1");
            user.setPermissionDirects(permissionDirects);
        }
        return user;
    }

    /**
     * 用户注册
     *
     * @param user
     * @return
     */
    @Override
    public String register(UserDto user) {
        String password = user.getPassword();
        User exist = getUserByName(user.getUsername());
        if (exist != null) {
            String message = "用户名已被使用";
            return message;
        }

        // 生成盐,默认长度 16 位
        String salt = new SecureRandomNumberGenerator().nextBytes().toString();
        // 设置 hash 算法迭代次数
        int times = 2;
        // 得到 hash 后的密码
        String encodedPassword = new SimpleHash("md5", password, salt, times).toString();
        // 存储用户信息，包括 salt 与 hash 后的密码
        user.setSalt(salt);
        user.setPassword(encodedPassword);
        return createUser(user) == 1 ? "注册成功" : "注册失败";
    }


}
