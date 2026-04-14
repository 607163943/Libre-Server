package com.libre.controller.user;

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
}
