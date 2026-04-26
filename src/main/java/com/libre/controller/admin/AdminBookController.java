package com.libre.controller.admin;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.libre.pojo.dto.admin.BookDTO;
import com.libre.pojo.dto.admin.BookPageDTO;
import com.libre.pojo.po.Book;
import com.libre.pojo.vo.admin.BookPageVO;
import com.libre.pojo.vo.admin.BookVO;
import com.libre.result.PageResult;
import com.libre.result.Result;
import com.libre.service.admin.AdminBookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "图书管理接口")
@RequestMapping("/admin/book")
@RestController
@RequiredArgsConstructor
public class AdminBookController {
    private final AdminBookService adminBookService;

    @ApiOperation("图书分页查询接口")
    @GetMapping
    public Result<PageResult<List<BookPageVO>>> pageQueryBook(BookPageDTO bookPageDTO) {
        PageResult<List<BookPageVO>> pageResult = adminBookService.pageQueryBook(bookPageDTO);
        return Result.success(pageResult);
    }

    @ApiOperation("获取指定图书信息")
    @GetMapping("{bookId}")
    public Result<BookVO> getBook(@PathVariable Long bookId) {
        Book book = adminBookService.getById(bookId);
        BookVO bookVO = BeanUtil.copyProperties(book, BookVO.class);
        return Result.success(bookVO);
    }

    @ApiOperation("获取所有图书")
    @GetMapping("all")
    public Result<List<BookVO>> getAllBooks() {
        List<Book> bookList = adminBookService.list();
        List<BookVO> bookVOList = BeanUtil.copyToList(bookList, BookVO.class);
        return Result.success(bookVOList);
    }

    @ApiOperation("图书添加接口")
    @PostMapping
    public Result<Void> addBook(@RequestBody @Valid BookDTO bookDTO) {
        adminBookService.addBook(bookDTO);
        return Result.success();
    }

    @ApiOperation("图书修改接口")
    @PutMapping
    public Result<Void> modifyBook(@RequestBody @Valid BookDTO bookDTO) {
        adminBookService.modifyBook(bookDTO);
        return Result.success();
    }

    @ApiOperation("图书删除接口")
    @DeleteMapping("{bookId}")
    public Result<Void> deleteBook(@PathVariable Long bookId) {
        adminBookService.deleteBook(bookId);
        return Result.success();
    }

    @ApiOperation("图书批量删除接口")
    @DeleteMapping
    public Result<Void> deleteBatchBook(@RequestParam List<Long> ids) {
        if(CollUtil.isNotEmpty(ids)) {
            adminBookService.deleteBatchBook(ids);
        }
        return Result.success();
    }
}
