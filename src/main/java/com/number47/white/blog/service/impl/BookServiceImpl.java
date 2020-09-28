package com.number47.white.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.number47.white.blog.dao.BookMapper;
import com.number47.white.blog.dto.BookDto;
import com.number47.white.blog.entity.Book;
import com.number47.white.blog.service.BookService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 图书 服务实现类
 * </p>
 *
 * @author number47
 * @since 2020-08-11
 */
@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, Book> implements BookService {
    @Autowired
    private BookMapper bookMapper;

    @Override
    public List<Book> listAllBook(BookDto bookDto) {
        Book book = new Book();
        BeanUtils.copyProperties(bookDto,book);
        QueryWrapper<Book> queryWrapper = new QueryWrapper<>(book);
        return bookMapper.selectList(queryWrapper);
    }

    @Override
    public int createBook(BookDto bookDto) {
        Book book = new Book();
        BeanUtils.copyProperties(bookDto,book);
        return bookMapper.insert(book);
    }

    @Override
    public int updateBook(Long id, BookDto bookDto) {
         Book book = new Book();
         BeanUtils.copyProperties(bookDto,book);
         book.setId(id);
         return bookMapper.updateById(book);
    }

    @Override
    public int deleteBook(Long id) {
         return bookMapper.deleteById(id);
    }

    @Override
    public IPage<Book> listBook(Page<Book> page, BookDto bookDto) {
        return bookMapper.findBookWithCategoryPage(page,bookDto);
    }

    @Override
    public Book getBook(Long id) {
         return bookMapper.selectById(id);
    }


}
