package com.number47.white.blog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.number47.white.blog.dto.BookDto;
import com.number47.white.blog.entity.Book;

import java.util.List;
/**
 * <p>
 * 图书 服务类
 * </p>
 *
 * @author number47
 * @since 2020-08-11
 */
public interface BookService extends IService<Book> {

   List<Book> listAllBook(BookDto bookDto);

   int createBook(BookDto bookDto);

   int updateBook(Long id, BookDto bookDto);

   int deleteBook(Long id);

    IPage<Book> listBook(Page<Book> page, BookDto bookDto);

    Book getBook(Long id);
}
