package com.number47.white.blog.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.number47.white.blog.system.dto.CategoryDto;
import com.number47.white.blog.system.entity.Book;
import com.number47.white.blog.system.entity.Category;

import java.util.List;
/**
 * <p>
 * 图书类别 服务类
 * </p>
 *
 * @author number47
 * @since 2020-08-11
 */
public interface CategoryService extends IService<Category> {
   List<Category> listAllCategory(CategoryDto categoryDto);

   int createCategory(CategoryDto categoryDto);

   int updateCategory(Long id, CategoryDto categoryDto);

   int deleteCategory(Long id);

    IPage<Category> listCategory(Page<Category> page, CategoryDto categoryDto);

    Category getCategory(Long id);

    IPage<Book> listBookByCategory(Page<Book> page, Long id);
}
