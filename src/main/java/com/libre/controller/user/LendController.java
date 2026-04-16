package com.libre.controller.user;

import com.libre.result.Result;
import com.libre.service.LendService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 用户端借阅管理接口
 */
@Api(tags = "用户端借阅管理接口")
@RequestMapping("/lend")
@RestController("user-lend")
@RequiredArgsConstructor
public class LendController {

    private final LendService lendService;

    @ApiOperation("借阅图书")
    @PostMapping("/{bookId}")
    public Result<Void> userLendBook(@PathVariable Long bookId) {
        lendService.userLendBook(bookId);
        return Result.success();
    }

    @ApiOperation("归还图书")
    @PatchMapping("/return/{bookId}")
    public Result<Void> userReturnBook(@PathVariable Long bookId) {
        lendService.userReturnBook(bookId);
        return Result.success();
    }
}