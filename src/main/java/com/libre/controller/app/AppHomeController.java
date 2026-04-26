package com.libre.controller.app;

import com.libre.pojo.vo.app.HomeTopLatestBookVO;
import com.libre.pojo.vo.app.HomeTopLendBookVO;
import com.libre.pojo.vo.app.HomeUserTotalVO;
import com.libre.result.Result;
import com.libre.service.app.AppHomeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "用户端首页接口")
@RequiredArgsConstructor
@RequestMapping("/user/home")
@RestController
public class AppHomeController {
    private final AppHomeService appHomeService;

    @ApiOperation("获取首页用户借阅信息")
    @GetMapping("/user/lend/total")
    public Result<HomeUserTotalVO> getHomeUserTotal() {
        HomeUserTotalVO homeUserTotalVO = appHomeService.getHomeUserTotal();
        return Result.success(homeUserTotalVO);
    }

    @ApiOperation("获取首页热门借阅书籍")
    @GetMapping("/top/lend/book")
    public Result<HomeTopLendBookVO> getHomeTopLendBook() {
        HomeTopLendBookVO homeTopLendBookVO = appHomeService.getHomeTopLendBook();
        return Result.success(homeTopLendBookVO);
    }

    @ApiOperation("获取首页最新入馆书籍")
    @GetMapping("/top/latest/book")
    public Result<HomeTopLatestBookVO> getHomeTopLatestBook() {
        HomeTopLatestBookVO homeTopLatestBookVO = appHomeService.getHomeTopLatestBook();
        return Result.success(homeTopLatestBookVO);
    }
}
