package com.libre.controller.user;

import com.libre.pojo.vo.user.BookDetailVO;
import com.libre.result.Result;
import com.libre.service.LendService;
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
@RestController("user-book-controller")
@RequiredArgsConstructor
public class BookController {

    private final LendService lendService;

    @ApiOperation("获取图书详情")
    @GetMapping("/{bookId}")
    public Result<BookDetailVO> getBookDetail(@PathVariable Long bookId) {
        BookDetailVO bookDetail = lendService.getBookDetail(bookId);
        return Result.success(bookDetail);
    }
}
