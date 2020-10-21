package com.number47.white.blog.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.number47.white.blog.common.CommonPage;
import com.number47.white.blog.common.CommonResult;
import com.number47.white.blog.system.dto.AdminPermissionDto;
import com.number47.white.blog.system.entity.AdminPermission;
import com.number47.white.blog.system.service.AdminPermissionService;
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
 * 权限/菜单 前端控制器
 * </p>
 *
 * @author number47
 * @since 2020-09-14
 */
@RestController
@Api(tags = "AdminPermissionController", description = "权限/菜单有关接口")
@RequestMapping("/api/adminPermission")
public class AdminPermissionController {
    @Autowired
    private AdminPermissionService  adminPermissionService;

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminPermissionController.class);
    /**
    * @Description: 获取权限/菜单全部列表
    * @Param: [adminPermissionDto]
    * @return:
    * @Author: number47
    * @Date: 2020-09-14
    */
    @ApiModelProperty(value = "获取权限/菜单全部列表")
    @RequestMapping(value = "listAll", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<AdminPermission>> getAdminPermissionList(AdminPermissionDto adminPermissionDto) {
        return CommonResult.success(adminPermissionService.listAllAdminPermission(adminPermissionDto));
    }

    /**
    * @Description: 添加权限/菜单
    * @Param: [adminPermission]
    * @return: com.number47.mall.common.CommonResult
    * @Author: number47
    * @Date: 2020-09-14
    */
    @ApiModelProperty(value = "添加权限/菜单")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult createAdminPermission(@Validated @RequestBody AdminPermissionDto adminPermissionDto,BindingResult result) {
         if (result.hasErrors()) {
            return CommonResult.validateFailed(result.getFieldError().getDefaultMessage());
         }
         CommonResult commonResult;
         int count = adminPermissionService.createAdminPermission(adminPermissionDto);
         if (count == 1) {
            commonResult = CommonResult.success(adminPermissionDto);
            LOGGER.debug("create AdminPermission success:{}", adminPermissionDto);
         } else {
            commonResult = CommonResult.failed("操作失败");
            LOGGER.debug("create AdminPermission failed:{}", adminPermissionDto);
         }
         return commonResult;
    }

    /**
    * @Description: 通过id更新权限/菜单
    * @Param: [id, adminPermission, result]
    * @return: com.number47.mall.common.CommonResult
    * @Author: number47
    * @Date: 2020-09-14
    */
    @ApiModelProperty(value = "通过id更新权限/菜单")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updateAdminPermission(@PathVariable("id") Long id, @Validated @RequestBody AdminPermissionDto adminPermissionDto, BindingResult result) {
        if(result.hasErrors()){
             return CommonResult.validateFailed(result.getFieldError().getDefaultMessage());
        }
        CommonResult commonResult;
        int count = adminPermissionService.updateAdminPermission(id, adminPermissionDto);
        if (count == 1) {
            commonResult = CommonResult.success(adminPermissionDto);
            LOGGER.debug("update adminPermission success:{}", adminPermissionDto);
        } else {
            commonResult = CommonResult.failed("操作失败");
            LOGGER.debug("update adminPermission failed:{}", adminPermissionDto);
        }
        return commonResult;
    }

    /**
    * @Description: 通过id删除权限/菜单
    * @Param: [id]
    * @return: com.number47.mall.common.CommonResult
    * @Author: number47
    * @Date: 2020-09-14
    */
    @ApiModelProperty(value = "通过id删除权限/菜单")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult deleteAdminPermission(@PathVariable("id") Long id) {
        int count = adminPermissionService.deleteAdminPermission(id);
        if (count == 1) {
            LOGGER.debug("delete AdminPermission success :id={}", id);
            return CommonResult.success(null);
        } else {
            LOGGER.debug("delete AdminPermission failed :id={}", id);
            return CommonResult.failed("操作失败");
        }
    }
    /**
    * @Description: 分页获取权限/菜单列表
    * @Param: [pageNum, pageSize, adminPermissionDto]
    * @return:
    * @Author: number47
    * @Date: 2020-09-14
    */
    @ApiModelProperty(value = "分页获取权限/菜单列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<CommonPage<AdminPermission>> listAdminPermission(AdminPermissionDto adminPermissionDto) {
        Page<AdminPermission> page = new Page<>(adminPermissionDto.getPageNum(), adminPermissionDto.getPageSize());
        IPage<AdminPermission> adminPermissionPage = adminPermissionService.listAdminPermission(page,adminPermissionDto);
        return CommonResult.success(CommonPage.restPage(adminPermissionPage));
    }

    /**
    * @Description: 通过id获取权限/菜单
    * @Param: [id]
    * @return:
    * @Author: number47
    * @Date: 2020-09-14
    */
    @ApiModelProperty(value = "通过id获取权限/菜单")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<AdminPermission> getAdminPermission(@PathVariable("id") Long id) {
        return CommonResult.success(adminPermissionService.getAdminPermission(id));
    }
}
