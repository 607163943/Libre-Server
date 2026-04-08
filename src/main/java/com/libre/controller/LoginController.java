package com.libre.controller;

import com.libre.pojo.dto.LoginDTO;
import com.libre.result.Result;
import com.libre.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "登录接口")
@RequiredArgsConstructor
@RestController
public class LoginController {
    private final LoginService loginService;

    @ApiOperation("登录接口")
    @PostMapping("/login")
    public Result<Void> login(@RequestBody LoginDTO loginDTO) {
        loginService.login(loginDTO);
        return Result.success();
    }

    @ApiOperation("登出接口")
    @PostMapping("/logout")
    public Result<Void> logout() {
        return Result.success();
    }
}
