package com.number47.white.blog.system.controller;
import cn.hutool.core.util.IdUtil;
import com.number47.white.blog.common.CommonResult;
import com.number47.white.blog.common.CommonPage;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.number47.white.blog.system.dto.BookDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.number47.white.blog.system.entity.Book;
import org.springframework.validation.BindingResult;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.number47.white.blog.system.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.IOException;
import java.util.List;
/**
 * <p>
 * 图书 前端控制器
 * </p>
 *
 * @author number47
 * @since 2020-08-11
 */
@RestController
@Api(tags = "BookController", description = "图书有关接口")
@RequestMapping("/api/book")
public class BookController {
    @Autowired
    private BookService  bookService;
    @Value("${img.path}")
    private String imgPath;
    @Value("${img.url}")
    private String imgUrl;

    private static final Logger LOGGER = LoggerFactory.getLogger(BookController.class);
    /**
    * @Description: 获取图书全部列表
    * @Param: [bookDto]
    * @return:
    * @Author: number47
    * @Date: 2020-08-11
    */
    @ApiModelProperty(value = "获取图书全部列表")
    @RequestMapping(value = "listAll", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<Book>> getBookList(BookDto bookDto) {
        return CommonResult.success(bookService.listAllBook(bookDto));
    }

    /**
    * @Description: 添加图书
    * @Param: [book]
    * @return: com.number47.mall.common.CommonResult
    * @Author: number47
    * @Date: 2020-08-11
    */
    @ApiModelProperty(value = "添加图书")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult createBook(@Validated @RequestBody BookDto bookDto,BindingResult result) {
         if (result.hasErrors()) {
            return CommonResult.validateFailed(result.getFieldError().getDefaultMessage());
         }
         CommonResult commonResult;
         int count = bookService.createBook(bookDto);
         if (count == 1) {
            commonResult = CommonResult.success(bookDto);
            LOGGER.debug("create Book success:{}", bookDto);
         } else {
            commonResult = CommonResult.failed("操作失败");
            LOGGER.debug("create Book failed:{}", bookDto);
         }
         return commonResult;
    }

    /**
    * @Description: 通过id更新图书
    * @Param: [id, book, result]
    * @return: com.number47.mall.common.CommonResult
    * @Author: number47
    * @Date: 2020-08-11
    */
    @ApiModelProperty(value = "通过id更新图书")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updateBook(@PathVariable("id") Long id, @Validated @RequestBody BookDto bookDto, BindingResult result) {
        if(result.hasErrors()){
             return CommonResult.validateFailed(result.getFieldError().getDefaultMessage());
        }
        CommonResult commonResult;
        int count = bookService.updateBook(id, bookDto);
        if (count == 1) {
            commonResult = CommonResult.success(bookDto);
            LOGGER.debug("update book success:{}", bookDto);
        } else {
            commonResult = CommonResult.failed("操作失败");
            LOGGER.debug("update book failed:{}", bookDto);
        }
        return commonResult;
    }

    /**
    * @Description: 通过id删除图书
    * @Param: [id]
    * @return: com.number47.mall.common.CommonResult
    * @Author: number47
    * @Date: 2020-08-11
    */
    @ApiModelProperty(value = "通过id删除图书")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult deleteBook(@PathVariable("id") Long id) {
        int count = bookService.deleteBook(id);
        if (count == 1) {
            LOGGER.debug("delete Book success :id={}", id);
            return CommonResult.success(null);
        } else {
            LOGGER.debug("delete Book failed :id={}", id);
            return CommonResult.failed("操作失败");
        }
    }
    /**
    * @Description: 分页获取图书列表
    * @Param: [pageNum, pageSize, bookDto]
    * @return:
    * @Author: number47
    * @Date: 2020-08-11
    */
    @ApiModelProperty(value = "分页获取图书列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<CommonPage<Book>> listBook(BookDto bookDto) {
        Page<Book> page = new Page<>(bookDto.getPageNum(), bookDto.getPageSize());
        IPage<Book> bookPage = bookService.listBook(page,bookDto);
        return CommonResult.success(CommonPage.restPage(bookPage));
    }

    /**
    * @Description: 通过id获取图书
    * @Param: [id]
    * @return:
    * @Author: number47
    * @Date: 2020-08-11
    */
    @ApiModelProperty(value = "通过id获取图书")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<Book> getBook(@PathVariable("id") Long id) {
        return CommonResult.success(bookService.getBook(id));
    }

    /**
     * @Description: 上传图片
     * @Param: [id]
     * @return:
     * @Author: number47
     * @Date: 2020-08-11
     */
    @ApiModelProperty(value = "通过id获取图书")
    @ResponseBody
    @PostMapping("/covers")
    public String coversUpload(MultipartFile file) throws Exception {
        String folder = imgPath;
        File imageFolder = new File(folder);
        File f = new File(imageFolder, IdUtil.objectId() + file.getOriginalFilename()
                .substring(file.getOriginalFilename().length() - 4));
        if (!f.getParentFile().exists())
            f.getParentFile().mkdirs();
        try {
            file.transferTo(f);
            String imgURL = imgUrl + f.getName();
            return imgURL;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }


}
