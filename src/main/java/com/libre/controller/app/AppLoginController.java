package com.libre.controller.app;

import com.libre.pojo.dto.common.LoginDTO;
import com.libre.pojo.dto.common.RegisterDTO;
import com.libre.pojo.vo.common.LoginVO;
import com.libre.result.Result;
import com.libre.service.common.CommonLoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api(tags = "用户端登录接口")
@RequiredArgsConstructor
@RestController
@RequestMapping("/app")
public class AppLoginController {
    private final CommonLoginService commonLoginService;

    @ApiOperation("用户登录")
    @PostMapping("/login")
    public Result<LoginVO> login(@RequestBody @Valid LoginDTO loginDTO) {
        LoginVO loginVO = commonLoginService.login(loginDTO);
        return Result.success(loginVO);
    }

    @ApiOperation("用户注册")
    @PostMapping("/register")
    public Result<Void> register(@RequestBody @Valid RegisterDTO registerDTO) {
        commonLoginService.register(registerDTO);
        return Result.success();
    }

    @ApiOperation("用户登出")
    @PostMapping("/logout")
    public Result<Void> logout() {
        commonLoginService.logout();
        return Result.success();
    }
}
