package com.number47.white.blog.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.number47.white.blog.system.dto.UserDto;
import com.number47.white.blog.system.entity.User;

import java.util.List;
/**
 * <p>
 *  服务类
 * </p>
 *
 * @author number47
 * @since 2020-08-11
 */
public interface UserService extends IService<User> {
   List<User> listAllUser(UserDto userDto);

   int createUser(UserDto userDto);

   int updateUser(Long id, UserDto userDto);

   int deleteUser(Long id);

    IPage<User> listUser(Page<User> page, UserDto userDto);

    User getUser(Long id);

    User getUserByName(String name);

    String register(UserDto user);
}
