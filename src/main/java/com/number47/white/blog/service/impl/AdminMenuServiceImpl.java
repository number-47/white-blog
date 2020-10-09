package com.number47.white.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.number47.white.blog.dao.AdminMenuMapper;
import com.number47.white.blog.dto.AdminRoleMenuDto;
import com.number47.white.blog.dto.UserDto;
import com.number47.white.blog.entity.AdminMenu;
import com.number47.white.blog.dto.AdminMenuDto;
import com.number47.white.blog.entity.AdminRoleMenu;
import com.number47.white.blog.entity.User;
import com.number47.white.blog.service.AdminMenuService;
import com.number47.white.blog.service.AdminRoleMenuService;
import com.number47.white.blog.service.AdminUserRoleService;
import com.number47.white.blog.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.lob.LobCreator;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 菜单 服务实现类
 * </p>
 *
 * @author number47
 * @since 2020-09-14
 */
@Service
public class AdminMenuServiceImpl extends ServiceImpl<AdminMenuMapper, AdminMenu> implements AdminMenuService {

    @Autowired
    private AdminMenuMapper adminMenuMapper;

    @Autowired
    private AdminRoleMenuService adminRoleMenuService;

    @Autowired
    private UserService userService;

    @Autowired
    private AdminUserRoleService adminUserRoleService;


    @Override
    public List<AdminMenu> listAllAdminMenu(AdminMenuDto adminMenuDto) {
        AdminMenu adminMenu = new AdminMenu();
        BeanUtils.copyProperties(adminMenuDto, adminMenu);
        LambdaQueryWrapper<AdminMenu> queryWrapper = new LambdaQueryWrapper<>(adminMenu);
        queryWrapper.orderByAsc(AdminMenu::getSequence);
        return adminMenuMapper.selectList(queryWrapper);
    }

    @Override
    public int createAdminMenu(AdminMenuDto adminMenuDto) {
        AdminMenu adminMenu = new AdminMenu();
        BeanUtils.copyProperties(adminMenuDto, adminMenu);
        return adminMenuMapper.insert(adminMenu);
    }

    @Override
    public int updateAdminMenu(Long id, AdminMenuDto adminMenuDto) {
        AdminMenu adminMenu = new AdminMenu();
        BeanUtils.copyProperties(adminMenuDto, adminMenu);
        adminMenu.setId(id);
        return adminMenuMapper.updateById(adminMenu);
    }

    @Override
    public int deleteAdminMenu(Long id) {
        return adminMenuMapper.deleteById(id);
    }

    @Override
    public IPage<AdminMenu> listAdminMenu(Page<AdminMenu> page, AdminMenuDto adminMenuDto) {
        AdminMenu adminMenu = new AdminMenu();
        BeanUtils.copyProperties(adminMenuDto, adminMenu);
        QueryWrapper<AdminMenu> queryWrapper = new QueryWrapper<>(adminMenu);
        return adminMenuMapper.selectPage(page, queryWrapper);
    }

    @Override
    public AdminMenu getAdminMenu(Long id) {
        return adminMenuMapper.selectById(id);
    }

    /**
     * 根据当前用户查询所有菜单选项
     *
     * @return
     */
    @Override
    public List<AdminMenu> getMenusByCurrentUser() {
        //从数据库获取当前用户
        String username = SecurityUtils.getSubject().getPrincipal().toString();
        User user = userService.getUserByName(username);
        //获取当前用户对应的所有角色的id列表
        List<Long> rids = adminUserRoleService.listRoleIdsByUid(user.getId());
        //查询出这些角色对应的菜单项
        List<Long> menuIds = new ArrayList<>();
        for (Long roleId:rids){
            AdminRoleMenuDto adminRoleMenuDto = new AdminRoleMenuDto();
            adminRoleMenuDto.setRid(roleId);
            List<Long> menus = adminRoleMenuService.listAllAdminRoleMenu(adminRoleMenuDto)
                    .stream().map(AdminRoleMenu::getMid).collect(Collectors.toList());
            if (menus != null){
                menuIds.addAll(menus);
            }
        }
        List<AdminMenu> menus = listByIds(menuIds).stream().distinct().collect(Collectors.toList());
        //处理菜单子节点
        handleMenus(menus);
        return menus;
    }

    /**
     * 整理菜单，处理父子节点关系
     * 1.遍历菜单项，根据每一项id查询该项的子节点，并放入children属性
     * 2.剔除所有子项，只保留第一层的父项。比如c是b的子项，b是a的子项，我们只要保留a就行，因为a包含了b,c
     * @param menus
     */
    private void handleMenus(List<AdminMenu> menus) {
        menus.forEach(m->{
            //根据父节点查询父节点的下一级子项目
            List<AdminMenu> children = getChildren(m.getId(),menus);
            m.setChildren(children);
        });

       /*
       Iterator<AdminMenu> iterator = menus.iterator();
        while (iterator.hasNext()){
            AdminMenu adminMenu = iterator.next();
            if (adminMenu.getParentId() != 0){
                iterator.remove();
            }
        }*/
        //上面代码使用lambda来写就是
        // menus.removeIf(m->m.getParentId() != 0);
        menus.removeIf(m->m.getParentId() != 0);
    }

    /**
     * 获取子节点
     * @param id 父节点
     * @param allMenus 所有菜单列表
     * @return
     */
    private List<AdminMenu> getChildren(Long id, List<AdminMenu> allMenus) {
        //子菜单
        List<AdminMenu> childList = new ArrayList<AdminMenu>();
        for (AdminMenu adminMenu:allMenus){
            //遍历所有节点，将所有菜单的父id与传过来的根节点id进行比较
            if (adminMenu.getParentId().equals(id)) {
                childList.add(adminMenu);
            }
        }
        for (AdminMenu menu:childList){
            menu.setChildren(getChildren(menu.getId(),allMenus));
        }
        if (childList.size() == 0){
            return new ArrayList<AdminMenu>();
        }
        return childList;
    }


}
