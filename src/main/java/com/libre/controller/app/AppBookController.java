package com.libre.controller.app;

import com.libre.pojo.vo.app.BookDetailVO;
import com.libre.result.Result;
import com.libre.service.app.AppBookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户端图书管理接口
 */
@Api(tags = "用户端图书管理接口")
@RequestMapping("/user/book")
@RestController
@RequiredArgsConstructor
public class AppBookController {
    private final AppBookService appBookService;

    @ApiOperation("获取图书详情")
    @GetMapping("/{bookId}")
    public Result<BookDetailVO> getBookDetail(@PathVariable Long bookId) {
        BookDetailVO bookDetail = appBookService.getBookDetail(bookId);
        return Result.success(bookDetail);
    }
}
