package com.number47.white.blog.system.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.number47.white.blog.common.CommonPage;
import com.number47.white.blog.common.CommonResult;
import com.number47.white.blog.system.dto.AdminUserRoleDto;
import com.number47.white.blog.system.dto.UserDto;
import com.number47.white.blog.system.entity.AdminUserRole;
import com.number47.white.blog.system.service.AdminUserRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 后台用户角色关联表 前端控制器
 * </p>
 *
 * @author number47
 * @since 2020-09-14
 */
@RestController
@Api(tags = "AdminUserRoleController", description = "后台用户角色关联表有关接口")
@RequestMapping("/api/adminUserRole")
public class AdminUserRoleController {
    @Autowired
    private AdminUserRoleService  adminUserRoleService;

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminUserRoleController.class);
    /**
    * @Description: 获取后台用户角色关联表全部列表
    * @Param: [adminUserRoleDto]
    * @return:
    * @Author: number47
    * @Date: 2020-09-14
    */
    @ApiModelProperty(value = "获取后台用户角色关联表全部列表")
    @RequestMapping(value = "listAll", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<AdminUserRole>> getAdminUserRoleList(AdminUserRoleDto adminUserRoleDto) {
        return CommonResult.success(adminUserRoleService.listAllAdminUserRole(adminUserRoleDto));
    }

    /**
    * @Description: 添加后台用户角色关联表
    * @Param: [adminUserRole]
    * @return: com.number47.mall.common.CommonResult
    * @Author: number47
    * @Date: 2020-09-14
    */
    @ApiModelProperty(value = "添加后台用户角色关联表")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult createAdminUserRole(@Validated @RequestBody AdminUserRoleDto adminUserRoleDto,BindingResult result) {
         if (result.hasErrors()) {
            return CommonResult.validateFailed(result.getFieldError().getDefaultMessage());
         }
         CommonResult commonResult;
         int count = adminUserRoleService.createAdminUserRole(adminUserRoleDto);
         if (count == 1) {
            commonResult = CommonResult.success(adminUserRoleDto);
            LOGGER.debug("create AdminUserRole success:{}", adminUserRoleDto);
         } else {
            commonResult = CommonResult.failed("操作失败");
            LOGGER.debug("create AdminUserRole failed:{}", adminUserRoleDto);
         }
         return commonResult;
    }

    /**
    * @Description: 通过id更新后台用户角色关联表
    * @Param: [id, adminUserRole, result]
    * @return: com.number47.mall.common.CommonResult
    * @Author: number47
    * @Date: 2020-09-14
    */
    @ApiModelProperty(value = "通过id更新后台用户角色关联表")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updateAdminUserRole(@PathVariable("id") Long id, @Validated @RequestBody AdminUserRoleDto adminUserRoleDto, BindingResult result) {
        if(result.hasErrors()){
             return CommonResult.validateFailed(result.getFieldError().getDefaultMessage());
        }
        CommonResult commonResult;
        int count = adminUserRoleService.updateAdminUserRole(id, adminUserRoleDto);
        if (count == 1) {
            commonResult = CommonResult.success(adminUserRoleDto);
            LOGGER.debug("update adminUserRole success:{}", adminUserRoleDto);
        } else {
            commonResult = CommonResult.failed("操作失败");
            LOGGER.debug("update adminUserRole failed:{}", adminUserRoleDto);
        }
        return commonResult;
    }

    /**
    * @Description: 通过id删除后台用户角色关联表
    * @Param: [id]
    * @return: com.number47.mall.common.CommonResult
    * @Author: number47
    * @Date: 2020-09-14
    */
    @ApiModelProperty(value = "通过id删除后台用户角色关联表")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult deleteAdminUserRole(@PathVariable("id") Long id) {
        int count = adminUserRoleService.deleteAdminUserRole(id);
        if (count == 1) {
            LOGGER.debug("delete AdminUserRole success :id={}", id);
            return CommonResult.success(null);
        } else {
            LOGGER.debug("delete AdminUserRole failed :id={}", id);
            return CommonResult.failed("操作失败");
        }
    }
    /**
    * @Description: 分页获取后台用户角色关联表列表
    * @Param: [pageNum, pageSize, adminUserRoleDto]
    * @return:
    * @Author: number47
    * @Date: 2020-09-14
    */
    @ApiModelProperty(value = "分页获取后台用户角色关联表列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<CommonPage<AdminUserRole>> listAdminUserRole(AdminUserRoleDto adminUserRoleDto) {
        Page<AdminUserRole> page = new Page<>(adminUserRoleDto.getPageNum(), adminUserRoleDto.getPageSize());
        IPage<AdminUserRole> adminUserRolePage = adminUserRoleService.listAdminUserRole(page,adminUserRoleDto);
        return CommonResult.success(CommonPage.restPage(adminUserRolePage));
    }

    /**
    * @Description: 通过id获取后台用户角色关联表
    * @Param: [id]
    * @return:
    * @Author: number47
    * @Date: 2020-09-14
    */
    @ApiModelProperty(value = "通过id获取后台用户角色关联表")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<AdminUserRole> getAdminUserRole(@PathVariable("id") Long id) {
        return CommonResult.success(adminUserRoleService.getAdminUserRole(id));
    }

    /**
     * 给角色分配用户
     * @param id 角色id
     * @param userDtos 用户列表
     * @return
     */
    @ApiModelProperty(value = "给角色分配用户")
    @RequestMapping(value = "/distributeUser/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult distributeUser(@PathVariable("id") Long id, @RequestBody List<UserDto> userDtos) {
        boolean result = false;
        if (CollectionUtil.isNotEmpty(userDtos)){
            List<String> userIds = userDtos.stream().map(UserDto::getId).collect(Collectors.toList());
            result = adminUserRoleService.createBathAdminUserRoleByUserIds(userIds,id.toString());
        }
        if (result) {
            LOGGER.debug("createBath AdminUserRole success :users={}", userDtos);
            return CommonResult.success("操作成功");
        } else {
            LOGGER.error("createBath AdminUserRole failed :users={}", userDtos);
            return CommonResult.failed("操作失败");
        }
    }
}
