package com.number47.white.blog.system.dao;

import com.number47.white.blog.system.entity.AdminMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 菜单 Mapper 接口
 * </p>
 *
 * @author number47
 * @since 2020-09-21
 */
@Repository
public interface AdminMenuMapper extends BaseMapper<AdminMenu> {

    List<String> listPermissionDirects(@Param("rIds") List<Long> rIds, @Param("type") String type);
}
