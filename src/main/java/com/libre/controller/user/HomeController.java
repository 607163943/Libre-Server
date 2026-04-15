package com.libre.controller.user;

import com.libre.pojo.vo.user.HomeTopLatestBookVO;
import com.libre.pojo.vo.user.HomeTopLendBookVO;
import com.libre.pojo.vo.user.HomeUserTotalVO;
import com.libre.result.Result;
import com.libre.service.HomeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "用户端首页接口")
@RequiredArgsConstructor
@RequestMapping("/home")
@RestController
public class HomeController {
    private final HomeService homeService;

    @ApiOperation("获取首页用户借阅信息")
    @GetMapping("/user/lend/total")
    public Result<HomeUserTotalVO> getHomeUserTotal() {
        HomeUserTotalVO homeUserTotalVO = homeService.getHomeUserTotal();
        return Result.success(homeUserTotalVO);
    }

    @ApiOperation("获取首页热门借阅书籍")
    @GetMapping("/top/lend/book")
    public Result<HomeTopLendBookVO> getHomeTopLendBook() {
        HomeTopLendBookVO homeTopLendBookVO = homeService.getHomeTopLendBook();
        return Result.success(homeTopLendBookVO);
    }

    @ApiOperation("获取首页最新入馆书籍")
    @GetMapping("/top/latest/book")
    public Result<HomeTopLatestBookVO> getHomeTopLatestBook() {
        HomeTopLatestBookVO homeTopLatestBookVO = homeService.getHomeTopLatestBook();
        return Result.success(homeTopLatestBookVO);
    }
}
