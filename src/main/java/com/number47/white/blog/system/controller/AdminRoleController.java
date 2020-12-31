package com.number47.white.blog.system.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.number47.white.blog.common.CommonPage;
import com.number47.white.blog.common.CommonResult;
import com.number47.white.blog.constant.CommonConstant;
import com.number47.white.blog.exception.FailRequestException;
import com.number47.white.blog.exception.SystemErrorException;
import com.number47.white.blog.system.dto.AdminRoleDto;
import com.number47.white.blog.system.dto.AdminRoleMenuDto;
import com.number47.white.blog.system.entity.AdminRole;
import com.number47.white.blog.system.entity.AdminRoleMenu;
import com.number47.white.blog.system.entity.User;
import com.number47.white.blog.system.service.AdminRoleMenuService;
import com.number47.white.blog.system.service.AdminRoleService;
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
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    @Autowired
    private AdminRoleMenuService adminRoleMenuService;

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
    public CommonResult<List<AdminRoleDto>> getAdminRoleList(AdminRoleDto adminRoleDto) {
        return CommonResult.success(handleRoleToRoleDto(adminRoleService.listAllAdminRole(adminRoleDto)));
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
         checkRoleName(adminRoleDto.getName(),CommonConstant.OPERATION_CREATE,null);
         int count = adminRoleService.createAdminRole(adminRoleDto);
         if (adminRoleDto.getMenuIds().size() > 0){
             adminRoleMenuService.createBathAdminRoleMenu(adminRoleDto.getMenuIds(),adminRoleDto.getId());
         }
         if (count == 1) {
            commonResult = CommonResult.success(adminRoleDto);
            LOGGER.debug("create AdminRole success:{}", adminRoleDto);
         } else {
            LOGGER.error("create AdminRole failed:{}", adminRoleDto);
            throw new SystemErrorException("操作失败，请联系管理员");
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
        checkRoleName(adminRoleDto.getName(),CommonConstant.OPERATION_UPDATE,id.toString());
        int count = adminRoleService.updateAdminRole(id, adminRoleDto);
        if (adminRoleDto.getMenuIds().size() > 0){
            adminRoleMenuService.createBathAdminRoleMenu(adminRoleDto.getMenuIds(),adminRoleDto.getId());
        }
        if (count == 1) {
            commonResult = CommonResult.success(adminRoleDto);
            LOGGER.debug("update adminRole success:{}", adminRoleDto);
        } else {
            LOGGER.debug("update adminRole failed:{}", adminRoleDto);
            throw new SystemErrorException("操作失败，请联系管理员");
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
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public CommonResult deleteAdminRole(@PathVariable("id") Long id) {
        int count = adminRoleService.deleteAdminRole(id);
        // 删除角色和菜单的关联关系
        int roleCount = adminRoleMenuService.deleteAdminRoleByRoleId(id);
        if (count == 1 && roleCount >= 0) {
            LOGGER.debug("delete AdminRole success :id={}", id);
            return CommonResult.success(null);
        } else {
            LOGGER.debug("delete AdminRole failed :id={}", id);
            throw new SystemErrorException("操作失败，请联系管理员");
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
    public CommonResult<CommonPage<AdminRoleDto>> listAdminRole(AdminRoleDto adminRoleDto) {
        Page<AdminRole> page = new Page<>(adminRoleDto.getPageNum(), adminRoleDto.getPageSize());
        IPage<AdminRole> adminRolePage = adminRoleService.listAdminRole(page,adminRoleDto);
        List<AdminRoleDto> adminRoleDtos = handleRoleToRoleDto(adminRolePage.getRecords());
        return CommonResult.success(CommonPage.replacePageList(adminRolePage,adminRoleDtos));
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

    /**
     * role 转 roleDto
     * @param adminRoles
     * @return
     */
    private List<AdminRoleDto> handleRoleToRoleDto(List<AdminRole> adminRoles){
        List<AdminRoleDto> adminRoleDtos = new ArrayList<>();
        if (adminRoles.size() > 0){
            for (AdminRole adminRole:adminRoles) {
                // 查找角色下菜单
                AdminRoleMenuDto adminRoleMenuDto = new AdminRoleMenuDto();
                adminRoleMenuDto.setRid(adminRole.getId());
                AdminRoleDto adminRoleDto = new AdminRoleDto();
                BeanUtils.copyProperties(adminRole,adminRoleDto);
                adminRoleDto.setId(adminRole.getId().toString());
                adminRoleDto.setLabel(adminRoleDto.getNameZh());
                List<AdminRoleMenu> adminRoleMenus = adminRoleMenuService.listAllAdminRoleMenu(adminRoleMenuDto);
                List<String> menusIds = new ArrayList<>(1);
                if (CollectionUtil.isNotEmpty(adminRoleMenus)){
                    adminRoleMenus.stream().map(AdminRoleMenu::getMid).forEach(id -> menusIds.add(String.valueOf(id)));
                    adminRoleDto.setMenuIds(menusIds);
                }
                adminRoleDtos.add(adminRoleDto);
            }
        }
        return adminRoleDtos;
    }

    /**
     * 检查角色名
     * @param roleName
     * @param operate
     * @param roleId
     */
    private void checkRoleName(String roleName,String operate, String roleId){
        AdminRole adminRole = adminRoleService.getRoleByName(roleName);
        if (CommonConstant.OPERATION_CREATE.equals(operate)){
            if (adminRole != null){
                throw new FailRequestException("角色名已存在");
            }
        }else if (CommonConstant.OPERATION_UPDATE.equals(operate)){
            if (adminRole != null && StrUtil.isNotBlank(roleId) && !roleId.equals(adminRole.getId().toString())){
                throw new FailRequestException("角色名已存在");
            }
        }
    }
}
