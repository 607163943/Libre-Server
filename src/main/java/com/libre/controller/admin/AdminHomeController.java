package com.libre.controller.admin;

import com.libre.pojo.vo.admin.HomeRecentLendTrendVO;
import com.libre.pojo.vo.admin.HomeTopBookVO;
import com.libre.pojo.vo.admin.HomeTotalCardVO;
import com.libre.result.Result;
import com.libre.service.admin.AdminHomeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "首页接口")
@RequiredArgsConstructor
@RequestMapping("/admin/home")
@RestController
public class AdminHomeController {
    private final AdminHomeService adminHomeService;

    @ApiOperation("获取首页统计数据")
    @GetMapping("/total/card")
    public Result<HomeTotalCardVO> getHomeTotalCard() {
        HomeTotalCardVO homeTotalCard = adminHomeService.getHomeTotalCard();
        return Result.success(homeTotalCard);
    }

    @ApiOperation("获取首页最近借阅趋势统计数据")
    @GetMapping("/recent/lend-trend")
    public Result<HomeRecentLendTrendVO> getHomeRecentLendTrend() {
        HomeRecentLendTrendVO homeRecentLendTrend = adminHomeService.getHomeRecentLendTrend();
        return Result.success(homeRecentLendTrend);
    }

    @ApiOperation("获取首页热门图书排行")
    @GetMapping("/top/book")
    public Result<HomeTopBookVO> getHomeTopBook() {
        HomeTopBookVO homeTopBook = adminHomeService.getHomeTopBook();
        return Result.success(homeTopBook);
    }
}
