package com.number47.white.blog.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.number47.white.blog.common.CommonPage;
import com.number47.white.blog.common.CommonResult;
import com.number47.white.blog.system.dto.CategoryDto;
import com.number47.white.blog.system.entity.Book;
import com.number47.white.blog.system.entity.Category;
import com.number47.white.blog.system.service.CategoryService;
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
 * 图书类别 前端控制器
 * </p>
 *
 * @author number47
 * @since 2020-08-11
 */
@RestController
@Api(tags = "CategoryController", description = "图书类别有关接口")
@RequestMapping("/api/category")
public class CategoryController {
    @Autowired
    private CategoryService  categoryService;

    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryController.class);
    /**
    * @Description: 获取图书类别全部列表
    * @Param: [categoryDto]
    * @return:
    * @Author: number47
    * @Date: 2020-08-11
    */
    @ApiModelProperty(value = "获取图书类别全部列表")
    @RequestMapping(value = "listAll", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<Category>> getCategoryList(CategoryDto categoryDto) {
        return CommonResult.success(categoryService.listAllCategory(categoryDto));
    }

    /**
    * @Description: 添加图书类别
    * @Param: [category]
    * @return: com.number47.mall.common.CommonResult
    * @Author: number47
    * @Date: 2020-08-11
    */
    @ApiModelProperty(value = "添加图书类别")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult createCategory(@Validated @RequestBody CategoryDto categoryDto,BindingResult result) {
         if (result.hasErrors()) {
            return CommonResult.validateFailed(result.getFieldError().getDefaultMessage());
         }
         CommonResult commonResult;
         int count = categoryService.createCategory(categoryDto);
         if (count == 1) {
            commonResult = CommonResult.success(categoryDto);
            LOGGER.debug("create Category success:{}", categoryDto);
         } else {
            commonResult = CommonResult.failed("操作失败");
            LOGGER.debug("create Category failed:{}", categoryDto);
         }
         return commonResult;
    }

    /**
    * @Description: 通过id更新图书类别
    * @Param: [id, category, result]
    * @return: com.number47.mall.common.CommonResult
    * @Author: number47
    * @Date: 2020-08-11
    */
    @ApiModelProperty(value = "通过id更新图书类别")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updateCategory(@PathVariable("id") Long id, @Validated @RequestBody CategoryDto categoryDto, BindingResult result) {
        if(result.hasErrors()){
             return CommonResult.validateFailed(result.getFieldError().getDefaultMessage());
        }
        CommonResult commonResult;
        int count = categoryService.updateCategory(id, categoryDto);
        if (count == 1) {
            commonResult = CommonResult.success(categoryDto);
            LOGGER.debug("update category success:{}", categoryDto);
        } else {
            commonResult = CommonResult.failed("操作失败");
            LOGGER.debug("update category failed:{}", categoryDto);
        }
        return commonResult;
    }

    /**
    * @Description: 通过id删除图书类别
    * @Param: [id]
    * @return: com.number47.mall.common.CommonResult
    * @Author: number47
    * @Date: 2020-08-11
    */
    @ApiModelProperty(value = "通过id删除图书类别")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult deleteCategory(@PathVariable("id") Long id) {
        int count = categoryService.deleteCategory(id);
        if (count == 1) {
            LOGGER.debug("delete Category success :id={}", id);
            return CommonResult.success(null);
        } else {
            LOGGER.debug("delete Category failed :id={}", id);
            return CommonResult.failed("操作失败");
        }
    }
    /**
    * @Description: 分页获取图书类别列表
    * @Param: [pageNum, pageSize, categoryDto]
    * @return:
    * @Author: number47
    * @Date: 2020-08-11
    */
    @ApiModelProperty(value = "分页获取图书类别列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<CommonPage<Category>> listCategory(CategoryDto categoryDto) {
        Page<Category> page = new Page<>(categoryDto.getPageNum(), categoryDto.getPageSize());
        IPage<Category> categoryPage = categoryService.listCategory(page,categoryDto);
        return CommonResult.success(CommonPage.restPage(categoryPage));
    }

    /**
    * @Description: 通过id获取图书类别
    * @Param: [id]
    * @return:
    * @Author: number47
    * @Date: 2020-08-11
    */
    @ApiModelProperty(value = "通过id获取图书类别")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<Category> getCategory(@PathVariable("id") Long id) {
        return CommonResult.success(categoryService.getCategory(id));
    }

    /**
     * @Description: 通过分类id获取图书，如果id=0则是全部，获取所有书籍
     * @Param: [id]
     * @return:
     * @Author: number47
     * @Date: 2020-08-11
     */
    @ApiModelProperty(value = "通过类别id获取图书")
    @RequestMapping(value = "/{id}/books/pageNum/{pageNum}/pageSize/{pageSize}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<CommonPage<Book>> listBookByCategory(@PathVariable("id") Long id,
                                                 @PathVariable("pageNum")Integer pageNum,
                                                 @PathVariable("pageSize")Integer pageSize ) {
        Page<Book> page = new Page<>(pageNum, pageSize);
        IPage<Book> booksPage = categoryService.listBookByCategory(page,id);
        return CommonResult.success(CommonPage.restPage(booksPage));
    }

}
