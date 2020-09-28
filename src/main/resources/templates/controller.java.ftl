package ${package.Controller};
import com.number47.white.blog.common.CommonResult;
import com.number47.white.blog.common.CommonPage;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ${package.Entity}.${entity};
import ${package.Entity}.${entity}Dto;
import org.springframework.validation.BindingResult;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.Api;
<#if restControllerStyle>
import org.springframework.web.bind.annotation.RestController;
<#else>
import org.springframework.stereotype.Controller;
</#if>
<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ${package.Service}.${table.serviceName};
import org.springframework.beans.factory.annotation.Autowired;


import java.util.List;
/**
 * <p>
 * ${table.comment!} 前端控制器
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if restControllerStyle>
@RestController
<#else>
@Controller
</#if>
<#if swagger2>
@Api(tags = "${table.controllerName}", description = "${table.comment!}有关接口")
</#if>
@RequestMapping("/api/${table.entityPath}")
<#if kotlin>
class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
<#if superControllerClass??>
public class ${table.controllerName} extends ${superControllerClass} {
<#else>
public class ${table.controllerName} {
</#if>
    @Autowired
    private ${table.serviceName}  ${'${entity}'? uncap_first}Service;

    private static final Logger LOGGER = LoggerFactory.getLogger(${table.controllerName}.class);
    /**
    * @Description: 获取${table.comment!}全部列表
    * @Param: [${'${entity}'? uncap_first}Dto]
    * @return:
    * @Author: ${author}
    * @Date: ${date}
    */
    <#if swagger2>
    @ApiModelProperty(value = "获取${table.comment!}全部列表")
    </#if>
    @RequestMapping(value = "listAll", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<${entity}>> get${entity}List(${entity}Dto ${'${entity}'? uncap_first}Dto) {
        return CommonResult.success(${'${entity}'? uncap_first}Service.listAll${entity}(${'${entity}'? uncap_first}Dto));
    }

    /**
    * @Description: 添加${table.comment!}
    * @Param: [${'${entity}'? uncap_first}]
    * @return: com.number47.mall.common.CommonResult
    * @Author: ${author}
    * @Date: ${date}
    */
    <#if swagger2>
    @ApiModelProperty(value = "添加${table.comment!}")
    </#if>
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult create${entity}(@Validated @RequestBody ${entity}Dto ${'${entity}'? uncap_first}Dto,BindingResult result) {
         if (result.hasErrors()) {
            return CommonResult.validateFailed(result.getFieldError().getDefaultMessage());
         }
         CommonResult commonResult;
         int count = ${'${entity}'? uncap_first}Service.create${entity}(${'${entity}'? uncap_first}Dto);
         if (count == 1) {
            commonResult = CommonResult.success(${'${entity}'? uncap_first}Dto);
            LOGGER.debug("create ${entity} success:{}", ${'${entity}'? uncap_first}Dto);
         } else {
            commonResult = CommonResult.failed("操作失败");
            LOGGER.debug("create ${entity} failed:{}", ${'${entity}'? uncap_first}Dto);
         }
         return commonResult;
    }

    /**
    * @Description: 通过id更新${table.comment!}
    * @Param: [id, ${'${entity}'? uncap_first}, result]
    * @return: com.number47.mall.common.CommonResult
    * @Author: ${author}
    * @Date: ${date}
    */
    <#if swagger2>
    @ApiModelProperty(value = "通过id更新${table.comment!}")
    </#if>
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult update${entity}(@PathVariable("id") Long id, @Validated @RequestBody ${entity}Dto ${'${entity}'? uncap_first}Dto, BindingResult result) {
        if(result.hasErrors()){
             return CommonResult.validateFailed(result.getFieldError().getDefaultMessage());
        }
        CommonResult commonResult;
        int count = ${'${entity}'? uncap_first}Service.update${entity}(id, ${'${entity}'? uncap_first}Dto);
        if (count == 1) {
            commonResult = CommonResult.success(${'${entity}'? uncap_first}Dto);
            LOGGER.debug("update ${'${entity}'? uncap_first} success:{}", ${'${entity}'? uncap_first}Dto);
        } else {
            commonResult = CommonResult.failed("操作失败");
            LOGGER.debug("update ${'${entity}'? uncap_first} failed:{}", ${'${entity}'? uncap_first}Dto);
        }
        return commonResult;
    }

    /**
    * @Description: 通过id删除${table.comment!}
    * @Param: [id]
    * @return: com.number47.mall.common.CommonResult
    * @Author: ${author}
    * @Date: ${date}
    */
    <#if swagger2>
    @ApiModelProperty(value = "通过id删除${table.comment!}")
    </#if>
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult delete${entity}(@PathVariable("id") Long id) {
        int count = ${'${entity}'? uncap_first}Service.delete${entity}(id);
        if (count == 1) {
            LOGGER.debug("delete ${entity} success :id={}", id);
            return CommonResult.success(null);
        } else {
            LOGGER.debug("delete ${entity} failed :id={}", id);
            return CommonResult.failed("操作失败");
        }
    }
    /**
    * @Description: 分页获取${table.comment!}列表
    * @Param: [pageNum, pageSize, ${'${entity}'? uncap_first}Dto]
    * @return:
    * @Author: ${author}
    * @Date: ${date}
    */
    <#if swagger2>
    @ApiModelProperty(value = "分页获取${table.comment!}列表")
    </#if>
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<CommonPage<${entity}>> list${entity}(${entity}Dto ${'${entity}'? uncap_first}Dto) {
        Page<${entity}> page = new Page<>(${'${entity}'? uncap_first}Dto.getPageNum(), ${'${entity}'? uncap_first}Dto.getPageSize());
        IPage<${entity}> ${'${entity}'? uncap_first}Page = ${'${entity}'? uncap_first}Service.list${entity}(page,${'${entity}'? uncap_first}Dto);
        return CommonResult.success(CommonPage.restPage(${'${entity}'? uncap_first}Page));
    }

    /**
    * @Description: 通过id获取${table.comment!}
    * @Param: [id]
    * @return:
    * @Author: ${author}
    * @Date: ${date}
    */
    <#if swagger2>
    @ApiModelProperty(value = "通过id获取${table.comment!}")
    </#if>
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<${entity}> get${entity}(@PathVariable("id") Long id) {
        return CommonResult.success(${'${entity}'? uncap_first}Service.get${entity}(id));
    }
}
</#if>
