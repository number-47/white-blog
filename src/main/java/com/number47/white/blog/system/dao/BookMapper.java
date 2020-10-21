package com.number47.white.blog.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.number47.white.blog.system.dto.BookDto;
import com.number47.white.blog.system.entity.Book;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
/**
 * <p>
 * 图书 Mapper 接口
 * </p>
 *
 * @author number47
 * @since 2020-08-11
 */
@Repository
public interface BookMapper extends BaseMapper<Book> {

    IPage<Book> findBookWithCategoryPage(@Param("page") Page<Book> page, @Param("bookDto") BookDto bookDto);
}
