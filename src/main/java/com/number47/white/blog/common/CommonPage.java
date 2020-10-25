package com.number47.white.blog.common;

import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * @program: mall
 * @description: 分页数据封装类
 * @author: number47
 * @create: 2020-08-05 14:08
 **/
public class CommonPage<T> {
    private Long pageNum;
    private Long pageSize;
    private Long totalPage;
    private Long total;
    private List<T> list;

    /**
     * 将PageHelper分页后的list转为分页信息
     */
    public static <T> CommonPage<T> restPage(IPage<T> pageInfo) {
        CommonPage<T> result = new CommonPage<T>();
        result.setTotalPage(pageInfo.getPages());
        result.setPageNum(pageInfo.getCurrent());
        result.setPageSize(pageInfo.getSize());
        result.setTotal(pageInfo.getTotal());
        result.setList(pageInfo.getRecords());
        return result;
    }

    /**
     * 将PageHelper分页后的list转为分页信息
     */
    public static <T, E> CommonPage<E> replacePageList(IPage<T> pageInfo, List<E> list) {
        CommonPage<E> result = new CommonPage<E>();
        result.setTotalPage(pageInfo.getPages());
        result.setPageNum(pageInfo.getCurrent());
        result.setPageSize(pageInfo.getSize());
        result.setTotal(pageInfo.getTotal());
        result.setList(list);
        return result;
    }

    public Long getPageNum() {
        return pageNum;
    }

    public void setPageNum(Long pageNum) {
        this.pageNum = pageNum;
    }

    public Long getPageSize() {
        return pageSize;
    }

    public void setPageSize(Long pageSize) {
        this.pageSize = pageSize;
    }

    public Long getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Long totalPage) {
        this.totalPage = totalPage;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}
