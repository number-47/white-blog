package com.number47.white.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.number47.white.blog.dao.UserMapper;
import com.number47.white.blog.dto.AdminRoleDto;
import com.number47.white.blog.dto.UserDto;
import com.number47.white.blog.entity.AdminRole;
import com.number47.white.blog.entity.AdminUserRole;
import com.number47.white.blog.entity.User;
import com.number47.white.blog.service.AdminRoleService;
import com.number47.white.blog.service.AdminUserRoleService;
import com.number47.white.blog.service.UserService;
import io.swagger.annotations.ApiModelProperty;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
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

    @Override
    public List<User> listAllUser(UserDto userDto) {
        User user = new User();
        BeanUtils.copyProperties(userDto,user);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>(user);
        return userMapper.selectList(queryWrapper);
    }

    @Override
    public int createUser(UserDto userDto) {
        User user = new User();
        BeanUtils.copyProperties(userDto,user);
        return userMapper.insert(user);
    }

    @Override
    public int updateUser(Long id, UserDto userDto) {
         User user = new User();
         BeanUtils.copyProperties(userDto,user);
         user.setId(id);
         return userMapper.updateById(user);
    }

    @Override
    public int deleteUser(Long id) {
         return userMapper.deleteById(id);
    }

    @Override
    public IPage<User> listUser(Page<User> page, UserDto userDto) {
        User user = new User();
        BeanUtils.copyProperties(userDto,user);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>(user);
        return userMapper.selectPage(page,queryWrapper);
    }

    @Override
    public User getUser(Long id) {
         return userMapper.selectById(id);
    }

    @Override
    public User getUserByName(String userName) {
        //查询用户
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<User>(new User());
        lambdaQueryWrapper.eq(User::getUsername,userName);
        User user = userMapper.selectOne(lambdaQueryWrapper);
        //查询用户角色
        List<Long> roleIds = adminUserRoleService.listRoleIdsByUid(user.getId());
        if (roleIds.size() > 0){
            List<AdminRole> adminRoles = adminRoleService.listByIds(roleIds);
            List<String> roles = new ArrayList<>();
            //过滤无效的角色
            adminRoles.stream().filter(m -> m.getEnabled().equals(true))
                    .map(AdminRole::getId).forEach(m -> roles.add(m.toString()));
            user.setRoles(roles);
        }
        return user;
    }

    /**
     * 用户注册
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
        return createUser(user) == 1? "注册成功":"注册失败";
    }
}
