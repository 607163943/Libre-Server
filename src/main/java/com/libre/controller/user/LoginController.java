package com.libre.controller.user;

import com.libre.pojo.dto.LoginDTO;
import com.libre.pojo.dto.RegisterDTO;
import com.libre.pojo.vo.LoginVO;
import com.libre.result.Result;
import com.libre.service.LoginService;
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
@RestController("user-login-controller")
@RequestMapping("/user")
public class LoginController {
    private final LoginService loginService;

    @ApiOperation("用户登录")
    @PostMapping("/login")
    public Result<LoginVO> login(@RequestBody @Valid LoginDTO loginDTO) {
        LoginVO loginVO = loginService.login(loginDTO);
        return Result.success(loginVO);
    }

    @ApiOperation("用户注册")
    @PostMapping("/register")
    public Result<Void> register(@RequestBody @Valid RegisterDTO registerDTO) {
        loginService.register(registerDTO);
        return Result.success();
    }

    @ApiOperation("用户登出")
    @PostMapping("/logout")
    public Result<Void> logout() {
        loginService.logout();
        return Result.success();
    }
}
