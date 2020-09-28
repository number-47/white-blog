package com.number47.white.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.number47.white.blog.dao.CategoryMapper;
import com.number47.white.blog.dto.BookDto;
import com.number47.white.blog.dto.CategoryDto;
import com.number47.white.blog.entity.Book;
import com.number47.white.blog.entity.Category;
import com.number47.white.blog.service.BookService;
import com.number47.white.blog.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 图书类别 服务实现类
 * </p>
 *
 * @author number47
 * @since 2020-08-11
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private BookService bookService;

    @Override
    public List<Category> listAllCategory(CategoryDto categoryDto) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryDto,category);
        QueryWrapper<Category> queryWrapper = new QueryWrapper<>(category);
        return categoryMapper.selectList(queryWrapper);
    }

    @Override
    public int createCategory(CategoryDto categoryDto) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryDto,category);
        return categoryMapper.insert(category);
    }

    @Override
    public int updateCategory(Long id, CategoryDto categoryDto) {
         Category category = new Category();
         BeanUtils.copyProperties(categoryDto,category);
         category.setId(id);
         return categoryMapper.updateById(category);
    }

    @Override
    public int deleteCategory(Long id) {
         return categoryMapper.deleteById(id);
    }

    @Override
    public IPage<Category> listCategory(Page<Category> page, CategoryDto categoryDto) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryDto,category);
        QueryWrapper<Category> queryWrapper = new QueryWrapper<>(category);
        return categoryMapper.selectPage(page,queryWrapper);
    }

    @Override
    public Category getCategory(Long id) {
         return categoryMapper.selectById(id);
    }

    /**
     * 通过分类id获取图书，如果id=0则是全部，获取所有书籍
     * @param page 分页
     * @param id 分类id
     * @return
     */
    @Override
    public IPage<Book> listBookByCategory(Page<Book> page,Long id) {
        BookDto bookDto = new BookDto();
        bookDto.setCid(id==0L?null:id);
        return bookService.listBook(page,bookDto);
    }
}
