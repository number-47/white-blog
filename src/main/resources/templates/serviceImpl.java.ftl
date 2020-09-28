package ${package.ServiceImpl};

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import ${package.Entity}.${entity};
import ${package.Entity}.${entity}Dto;
import ${package.Mapper}.${table.mapperName};
import ${package.Service}.${table.serviceName};
import ${superServiceImplClassPackage};
import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * <p>
 * ${table.comment!} 服务实现类
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
@Service
<#if kotlin>
open class ${table.serviceImplName} : ${superServiceImplClass}<${table.mapperName}, ${entity}>(), ${table.serviceName} {

}
<#else>
public class ${table.serviceImplName} extends ${superServiceImplClass}<${table.mapperName}, ${entity}> implements ${table.serviceName} {
    @Autowired
    private ${table.mapperName} ${'${table.mapperName}'? uncap_first};

    @Override
    public List<${entity}> listAll${entity}(${entity}Dto ${'${entity}'? uncap_first}Dto) {
        ${entity} ${'${entity}'? uncap_first} = new ${entity}();
        BeanUtils.copyProperties(${'${entity}'? uncap_first}Dto,${'${entity}'? uncap_first});
        QueryWrapper<${entity}> queryWrapper = new QueryWrapper<>(${'${entity}'? uncap_first});
        return ${'${table.mapperName}'? uncap_first}.selectList(queryWrapper);
    }

    @Override
    public int create${entity}(${entity}Dto ${'${entity}'? uncap_first}Dto) {
        ${entity} ${'${entity}'? uncap_first} = new ${entity}();
        BeanUtils.copyProperties(${'${entity}'? uncap_first}Dto,${'${entity}'? uncap_first});
        return ${'${table.mapperName}'? uncap_first}.insert(${'${entity}'? uncap_first});
    }

    @Override
    public int update${entity}(Long id, ${entity}Dto ${'${entity}'? uncap_first}Dto) {
         ${entity} ${'${entity}'? uncap_first} = new ${entity}();
         BeanUtils.copyProperties(${'${entity}'? uncap_first}Dto,${'${entity}'? uncap_first});
         ${'${entity}'? uncap_first}.setId(id);
         return ${'${table.mapperName}'? uncap_first}.updateById(${'${entity}'? uncap_first});
    }

    @Override
    public int delete${entity}(Long id) {
         return ${'${table.mapperName}'? uncap_first}.deleteById(id);
    }

    @Override
    public IPage<${entity}> list${entity}(Page<${entity}> page, ${entity}Dto ${'${entity}'? uncap_first}Dto) {
        ${entity} ${'${entity}'? uncap_first} = new ${entity}();
        BeanUtils.copyProperties(${'${entity}'? uncap_first}Dto,${'${entity}'? uncap_first});
        QueryWrapper<${entity}> queryWrapper = new QueryWrapper<>(${'${entity}'? uncap_first});
        return ${'${table.mapperName}'? uncap_first}.selectPage(page,queryWrapper);
    }

    @Override
    public ${entity} get${entity}(Long id) {
         return ${'${table.mapperName}'? uncap_first}.selectById(id);
    }
}
</#if>
