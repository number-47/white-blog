package ${package.Service};

import ${package.Entity}.${entity};
import ${package.Entity}.${entity}Dto;
import ${superServiceClassPackage};
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.List;
/**
 * <p>
 * ${table.comment!} 服务类
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if kotlin>
interface ${table.serviceName} : ${superServiceClass}<${entity}>
<#else>
public interface ${table.serviceName} extends ${superServiceClass}<${entity}> {
   List<${entity}> listAll${entity}(${entity}Dto ${'${entity}'? uncap_first}Dto);

   int create${entity}(${entity}Dto ${'${entity}'? uncap_first}Dto);

   int update${entity}(Long id, ${entity}Dto ${'${entity}'? uncap_first}Dto);

   int delete${entity}(Long id);

    IPage<${entity}> list${entity}(Page<${entity}> page, ${entity}Dto ${'${entity}'? uncap_first}Dto);

    ${entity} get${entity}(Long id);
}
</#if>
