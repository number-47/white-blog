package com.number47.white.blog.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.number47.white.blog.common.CommonPage;
import com.number47.white.blog.common.CommonResult;
import com.number47.white.blog.dto.AdminMenuDto;
import com.number47.white.blog.dto.Meta;
import com.number47.white.blog.entity.AdminMenu;
import com.number47.white.blog.entity.AdminRole;
import com.number47.white.blog.entity.AdminRoleMenu;
import com.number47.white.blog.service.AdminMenuService;
import com.number47.white.blog.service.AdminRoleMenuService;
import com.number47.white.blog.service.AdminRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
/**
 * <p>
 * 菜单 前端控制器
 * </p>
 *
 * @author number47
 * @since 2020-09-14
 */
@RestController
@Api(tags = "AdminMenuController", description = "菜单有关接口")
@RequestMapping("/api/adminMenu")
public class AdminMenuController {
    @Autowired
    private AdminMenuService  adminMenuService;
    @Autowired
    private AdminRoleService adminRoleService;

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminMenuController.class);
    /**
    * @Description: 获取菜单全部列表
    * @Param: [adminMenuDto]
    * @return:
    * @Author: number47
    * @Date: 2020-09-14
    */
    @ApiModelProperty(value = "获取菜单全部列表")
    @RequestMapping(value = "listAll", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<AdminMenu>> getAdminMenuList(AdminMenuDto adminMenuDto) {
        return CommonResult.success(adminMenuService.listAllAdminMenu(adminMenuDto));
    }

    /**
    * @Description: 添加菜单
    * @Param: [adminMenu]
    * @return: com.number47.mall.common.CommonResult
    * @Author: number47
    * @Date: 2020-09-14
    */
    @ApiModelProperty(value = "添加菜单")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult createAdminMenu(@Validated @RequestBody AdminMenuDto adminMenuDto,BindingResult result) {
         if (result.hasErrors()) {
            return CommonResult.validateFailed(result.getFieldError().getDefaultMessage());
         }
         CommonResult commonResult;
         int count = adminMenuService.createAdminMenu(adminMenuDto);
         if (count == 1) {
            commonResult = CommonResult.success(adminMenuDto);
            LOGGER.debug("create AdminMenu success:{}", adminMenuDto);
         } else {
            commonResult = CommonResult.failed("操作失败");
            LOGGER.debug("create AdminMenu failed:{}", adminMenuDto);
         }
         return commonResult;
    }

    /**
    * @Description: 通过id更新菜单
    * @Param: [id, adminMenu, result]
    * @return: com.number47.mall.common.CommonResult
    * @Author: number47
    * @Date: 2020-09-14
    */
    @ApiModelProperty(value = "通过id更新菜单")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updateAdminMenu(@PathVariable("id") Long id, @Validated @RequestBody AdminMenuDto adminMenuDto, BindingResult result) {
        if(result.hasErrors()){
             return CommonResult.validateFailed(result.getFieldError().getDefaultMessage());
        }
        CommonResult commonResult;
        int count = adminMenuService.updateAdminMenu(id, adminMenuDto);
        if (count == 1) {
            commonResult = CommonResult.success(adminMenuDto);
            LOGGER.debug("update adminMenu success:{}", adminMenuDto);
        } else {
            commonResult = CommonResult.failed("操作失败");
            LOGGER.debug("update adminMenu failed:{}", adminMenuDto);
        }
        return commonResult;
    }

    /**
    * @Description: 通过id删除菜单
    * @Param: [id]
    * @return: com.number47.mall.common.CommonResult
    * @Author: number47
    * @Date: 2020-09-14
    */
    @ApiModelProperty(value = "通过id删除菜单")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult deleteAdminMenu(@PathVariable("id") Long id) {
        int count = adminMenuService.deleteAdminMenu(id);
        if (count == 1) {
            LOGGER.debug("delete AdminMenu success :id={}", id);
            return CommonResult.success(null);
        } else {
            LOGGER.debug("delete AdminMenu failed :id={}", id);
            return CommonResult.failed("操作失败");
        }
    }
    /**
    * @Description: 分页获取菜单列表
    * @Param: [pageNum, pageSize, adminMenuDto]
    * @return:
    * @Author: number47
    * @Date: 2020-09-14
    */
    @ApiModelProperty(value = "分页获取菜单列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<CommonPage<AdminMenu>> listAdminMenu(AdminMenuDto adminMenuDto) {
        Page<AdminMenu> page = new Page<>(adminMenuDto.getPageNum(), adminMenuDto.getPageSize());
        IPage<AdminMenu> adminMenuPage = adminMenuService.listAdminMenu(page,adminMenuDto);
        return CommonResult.success(CommonPage.restPage(adminMenuPage));
    }

    /**
    * @Description: 通过id获取菜单
    * @Param: [id]
    * @return:
    * @Author: number47
    * @Date: 2020-09-14
    */
    @ApiModelProperty(value = "通过id获取菜单")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<AdminMenu> getAdminMenu(@PathVariable("id") Long id) {
        return CommonResult.success(adminMenuService.getAdminMenu(id));
    }

    /**
     * @Description: 获取当前用户有权限的菜单
     * @Param: [id]
     * @return:
     * @Author: number47
     * @Date: 2020-09-14
     */
    @ApiModelProperty(value = "通过id获取菜单")
    @RequestMapping(value = "/menu", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<AdminMenuDto>> getMenu() {
        //todo 管理员看到所有菜单
        List<AdminMenu> adminMenus = adminMenuService.getMenusByCurrentUser();
        return CommonResult.success(handleMenusToAdminMenuDto(adminMenus));
    }

    /**
     * 设置菜单格式
     * @param adminMenus
     */
    private List<AdminMenuDto> handleMenusToAdminMenuDto(List<AdminMenu> adminMenus) {
        List<AdminMenuDto> adminMenuDtos = new ArrayList<>();
        for (AdminMenu menu:adminMenus) {
            AdminMenuDto adminMenuDto = new AdminMenuDto();
            BeanUtils.copyProperties(menu, adminMenuDto);
            addMeta(menu,adminMenuDto);
            if (menu.getChildren().size()>0){
                adminMenuDto.setChildren(handleMenusToAdminMenuDto(menu.getChildren()));
            }
            adminMenuDtos.add(adminMenuDto);
        }
        return adminMenuDtos;
    }

    /**
     * 设置菜单的Meta
     * @param menu
     * @param adminMenuDto
     */
    private void addMeta(AdminMenu menu, AdminMenuDto adminMenuDto) {
        Meta meta = new Meta();
        meta.setIcon(menu.getIcon());
        meta.setTitle(menu.getTitle());
        //查询菜单有权限的角色
        List<AdminRole> adminRoles = adminRoleService.getAdminRolesByMenuId(menu.getId());
        meta.setRoles(adminRoles);
        adminMenuDto.setMeta(meta);
    }
}
