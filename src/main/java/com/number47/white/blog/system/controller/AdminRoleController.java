package com.number47.white.blog.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.number47.white.blog.common.CommonPage;
import com.number47.white.blog.common.CommonResult;
import com.number47.white.blog.system.dto.AdminRoleDto;
import com.number47.white.blog.system.entity.AdminRole;
import com.number47.white.blog.system.service.AdminRoleService;
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
 * 后台角色 前端控制器
 * </p>
 *
 * @author number47
 * @since 2020-09-14
 */
@RestController
@Api(tags = "AdminRoleController", description = "后台角色有关接口")
@RequestMapping("/api/adminRole")
public class AdminRoleController {
    @Autowired
    private AdminRoleService  adminRoleService;

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminRoleController.class);
    /**
    * @Description: 获取后台角色全部列表
    * @Param: [adminRoleDto]
    * @return:
    * @Author: number47
    * @Date: 2020-09-14
    */
    @ApiModelProperty(value = "获取后台角色全部列表")
    @RequestMapping(value = "listAll", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<AdminRole>> getAdminRoleList(AdminRoleDto adminRoleDto) {
        return CommonResult.success(adminRoleService.listAllAdminRole(adminRoleDto));
    }

    /**
    * @Description: 添加后台角色
    * @Param: [adminRole]
    * @return: com.number47.mall.common.CommonResult
    * @Author: number47
    * @Date: 2020-09-14
    */
    @ApiModelProperty(value = "添加后台角色")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult createAdminRole(@Validated @RequestBody AdminRoleDto adminRoleDto,BindingResult result) {
         if (result.hasErrors()) {
            return CommonResult.validateFailed(result.getFieldError().getDefaultMessage());
         }
         CommonResult commonResult;
         int count = adminRoleService.createAdminRole(adminRoleDto);
         if (count == 1) {
            commonResult = CommonResult.success(adminRoleDto);
            LOGGER.debug("create AdminRole success:{}", adminRoleDto);
         } else {
            commonResult = CommonResult.failed("操作失败");
            LOGGER.debug("create AdminRole failed:{}", adminRoleDto);
         }
         return commonResult;
    }

    /**
    * @Description: 通过id更新后台角色
    * @Param: [id, adminRole, result]
    * @return: com.number47.mall.common.CommonResult
    * @Author: number47
    * @Date: 2020-09-14
    */
    @ApiModelProperty(value = "通过id更新后台角色")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updateAdminRole(@PathVariable("id") Long id, @Validated @RequestBody AdminRoleDto adminRoleDto, BindingResult result) {
        if(result.hasErrors()){
             return CommonResult.validateFailed(result.getFieldError().getDefaultMessage());
        }
        CommonResult commonResult;
        int count = adminRoleService.updateAdminRole(id, adminRoleDto);
        if (count == 1) {
            commonResult = CommonResult.success(adminRoleDto);
            LOGGER.debug("update adminRole success:{}", adminRoleDto);
        } else {
            commonResult = CommonResult.failed("操作失败");
            LOGGER.debug("update adminRole failed:{}", adminRoleDto);
        }
        return commonResult;
    }

    /**
    * @Description: 通过id删除后台角色
    * @Param: [id]
    * @return: com.number47.mall.common.CommonResult
    * @Author: number47
    * @Date: 2020-09-14
    */
    @ApiModelProperty(value = "通过id删除后台角色")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult deleteAdminRole(@PathVariable("id") Long id) {
        int count = adminRoleService.deleteAdminRole(id);
        if (count == 1) {
            LOGGER.debug("delete AdminRole success :id={}", id);
            return CommonResult.success(null);
        } else {
            LOGGER.debug("delete AdminRole failed :id={}", id);
            return CommonResult.failed("操作失败");
        }
    }
    /**
    * @Description: 分页获取后台角色列表
    * @Param: [pageNum, pageSize, adminRoleDto]
    * @return:
    * @Author: number47
    * @Date: 2020-09-14
    */
    @ApiModelProperty(value = "分页获取后台角色列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<CommonPage<AdminRole>> listAdminRole(AdminRoleDto adminRoleDto) {
        Page<AdminRole> page = new Page<>(adminRoleDto.getPageNum(), adminRoleDto.getPageSize());
        IPage<AdminRole> adminRolePage = adminRoleService.listAdminRole(page,adminRoleDto);
        return CommonResult.success(CommonPage.restPage(adminRolePage));
    }

    /**
    * @Description: 通过id获取后台角色
    * @Param: [id]
    * @return:
    * @Author: number47
    * @Date: 2020-09-14
    */
    @ApiModelProperty(value = "通过id获取后台角色")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<AdminRole> getAdminRole(@PathVariable("id") Long id) {
        return CommonResult.success(adminRoleService.getAdminRole(id));
    }
}
