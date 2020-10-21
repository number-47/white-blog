package com.number47.white.blog.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.number47.white.blog.common.CommonPage;
import com.number47.white.blog.common.CommonResult;
import com.number47.white.blog.system.dto.AdminRoleMenuDto;
import com.number47.white.blog.system.entity.AdminRoleMenu;
import com.number47.white.blog.system.service.AdminRoleMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * <p>
 * 角色菜单关联关系 前端控制器
 * </p>
 *
 * @author number47
 * @since 2020-09-14
 */
@RestController
@Api(tags = "AdminRoleMenuController", description = "角色菜单关联关系有关接口")
@RequestMapping("/api/adminRoleMenu")
public class AdminRoleMenuController {
    @Autowired
    private AdminRoleMenuService  adminRoleMenuService;

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminRoleMenuController.class);
    /**
    * @Description: 获取角色菜单关联关系全部列表
    * @Param: [adminRoleMenuDto]
    * @return:
    * @Author: number47
    * @Date: 2020-09-14
    */
    @ApiModelProperty(value = "获取角色菜单关联关系全部列表")
    @RequestMapping(value = "listAll", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<AdminRoleMenu>> getAdminRoleMenuList(AdminRoleMenuDto adminRoleMenuDto) {
        return CommonResult.success(adminRoleMenuService.listAllAdminRoleMenu(adminRoleMenuDto));
    }

    /**
    * @Description: 添加角色菜单关联关系
    * @Param: [adminRoleMenu]
    * @return: com.number47.mall.common.CommonResult
    * @Author: number47
    * @Date: 2020-09-14
    */
    @ApiModelProperty(value = "添加角色菜单关联关系")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult createAdminRoleMenu(@Validated @RequestBody AdminRoleMenuDto adminRoleMenuDto,BindingResult result) {
         if (result.hasErrors()) {
            return CommonResult.validateFailed(result.getFieldError().getDefaultMessage());
         }
         CommonResult commonResult;
         int count = adminRoleMenuService.createAdminRoleMenu(adminRoleMenuDto);
         if (count == 1) {
            commonResult = CommonResult.success(adminRoleMenuDto);
            LOGGER.debug("create AdminRoleMenu success:{}", adminRoleMenuDto);
         } else {
            commonResult = CommonResult.failed("操作失败");
            LOGGER.debug("create AdminRoleMenu failed:{}", adminRoleMenuDto);
         }
         return commonResult;
    }

    /**
    * @Description: 通过id更新角色菜单关联关系
    * @Param: [id, adminRoleMenu, result]
    * @return: com.number47.mall.common.CommonResult
    * @Author: number47
    * @Date: 2020-09-14
    */
    @ApiModelProperty(value = "通过id更新角色菜单关联关系")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updateAdminRoleMenu(@PathVariable("id") Long id, @Validated @RequestBody AdminRoleMenuDto adminRoleMenuDto, BindingResult result) {
        if(result.hasErrors()){
             return CommonResult.validateFailed(result.getFieldError().getDefaultMessage());
        }
        CommonResult commonResult;
        int count = adminRoleMenuService.updateAdminRoleMenu(id, adminRoleMenuDto);
        if (count == 1) {
            commonResult = CommonResult.success(adminRoleMenuDto);
            LOGGER.debug("update adminRoleMenu success:{}", adminRoleMenuDto);
        } else {
            commonResult = CommonResult.failed("操作失败");
            LOGGER.debug("update adminRoleMenu failed:{}", adminRoleMenuDto);
        }
        return commonResult;
    }

    /**
    * @Description: 通过id删除角色菜单关联关系
    * @Param: [id]
    * @return: com.number47.mall.common.CommonResult
    * @Author: number47
    * @Date: 2020-09-14
    */
    @ApiModelProperty(value = "通过id删除角色菜单关联关系")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult deleteAdminRoleMenu(@PathVariable("id") Long id) {
        int count = adminRoleMenuService.deleteAdminRoleMenu(id);
        if (count == 1) {
            LOGGER.debug("delete AdminRoleMenu success :id={}", id);
            return CommonResult.success(null);
        } else {
            LOGGER.debug("delete AdminRoleMenu failed :id={}", id);
            return CommonResult.failed("操作失败");
        }
    }
    /**
    * @Description: 分页获取角色菜单关联关系列表
    * @Param: [pageNum, pageSize, adminRoleMenuDto]
    * @return:
    * @Author: number47
    * @Date: 2020-09-14
    */
    @ApiModelProperty(value = "分页获取角色菜单关联关系列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<CommonPage<AdminRoleMenu>> listAdminRoleMenu(AdminRoleMenuDto adminRoleMenuDto) {
        Page<AdminRoleMenu> page = new Page<>(adminRoleMenuDto.getPageNum(), adminRoleMenuDto.getPageSize());
        IPage<AdminRoleMenu> adminRoleMenuPage = adminRoleMenuService.listAdminRoleMenu(page,adminRoleMenuDto);
        return CommonResult.success(CommonPage.restPage(adminRoleMenuPage));
    }

    /**
    * @Description: 通过id获取角色菜单关联关系
    * @Param: [id]
    * @return:
    * @Author: number47
    * @Date: 2020-09-14
    */
    @ApiModelProperty(value = "通过id获取角色菜单关联关系")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<AdminRoleMenu> getAdminRoleMenu(@PathVariable("id") Long id) {
        return CommonResult.success(adminRoleMenuService.getAdminRoleMenu(id));
    }
}
