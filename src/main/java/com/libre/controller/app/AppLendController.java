package com.libre.controller.app;

import com.libre.pojo.dto.app.MyLendPageDTO;
import com.libre.pojo.vo.app.MyLendBookVO;
import com.libre.pojo.vo.app.MyLendDataVO;
import com.libre.pojo.vo.app.MyLendHistoryBookVO;
import com.libre.pojo.vo.app.MyLendHistoryDataVO;
import com.libre.result.PageResult;
import com.libre.result.Result;
import com.libre.service.app.AppLendService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户端借阅管理接口
 */
@Api(tags = "用户端借阅管理接口")
@RequestMapping("/app/lend")
@RestController
@RequiredArgsConstructor
public class AppLendController {
    private final AppLendService lendService;

    @ApiOperation("借阅图书")
    @PostMapping("/{bookId}")
    public Result<Void> userLendBook(@PathVariable Long bookId) {
        lendService.userLendBook(bookId);
        return Result.success();
    }

    @ApiOperation("续借图书")
    @PatchMapping("/renew/{bookId}")
    public Result<Void> userRenewBook(@PathVariable Long bookId) {
        lendService.userRenewBook(bookId);
        return Result.success();
    }

    @ApiOperation("归还图书")
    @PatchMapping("/return/{bookId}")
    public Result<Void> userReturnBook(@PathVariable Long bookId) {
        lendService.userReturnBook(bookId);
        return Result.success();
    }

    @ApiOperation("获取用户借阅数据统计")
    @GetMapping("/my-lend/data")
    public Result<MyLendDataVO> getMyLendData() {
        MyLendDataVO myLendData = lendService.getMyLendData();
        return Result.success(myLendData);
    }

    @ApiOperation("获取用户历史借阅数据统计")
    @GetMapping("/my-lend/history/data")
    public Result<MyLendHistoryDataVO> getMyLendHistoryData() {
        MyLendHistoryDataVO myLendHistoryData = lendService.getMyLendHistoryData();
        return Result.success(myLendHistoryData);
    }

    @ApiOperation("分页查询用户借阅书籍")
    @GetMapping("/my-lend")
    public Result<PageResult<List<MyLendBookVO>>> pageQueryMyLend(MyLendPageDTO myLendPageDTO) {
        PageResult<List<MyLendBookVO>> pageResult = lendService.pageQueryMyLend(myLendPageDTO);
        return Result.success(pageResult);
    }

    @ApiOperation("分页查询用户历史借阅书籍")
    @GetMapping("/my-lend/history")
    public Result<PageResult<List<MyLendHistoryBookVO>>> pageQueryMyLendHistory(MyLendPageDTO myLendPageDTO) {
        PageResult<List<MyLendHistoryBookVO>> pageResult = lendService.pageQueryMyLendHistory(myLendPageDTO);
        return Result.success(pageResult);
    }
}