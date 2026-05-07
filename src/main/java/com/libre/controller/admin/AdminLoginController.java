package com.libre.controller.admin;

import com.libre.pojo.dto.common.CaptchaDTO;
import com.libre.pojo.dto.common.LoginDTO;
import com.libre.pojo.vo.common.CaptchaVO;
import com.libre.pojo.vo.common.LoginVO;
import com.libre.result.Result;
import com.libre.service.common.CommonLoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "登录接口")
@RequestMapping("/admin")
@RequiredArgsConstructor
@RestController
public class AdminLoginController {
    private final CommonLoginService commonLoginService;

    @ApiOperation("登录接口")
    @PostMapping("/login")
    public Result<LoginVO> login(@RequestBody @Valid LoginDTO loginDTO) {
        LoginVO loginVO = commonLoginService.login(loginDTO);
        return Result.success(loginVO);
    }

    @ApiOperation("获取验证码")
    @PutMapping("/login/captcha")
    public Result<CaptchaVO> getCaptcha(@RequestBody CaptchaDTO captchaDTO) {
        CaptchaVO captchaVO=commonLoginService.getCaptcha(captchaDTO);
        return Result.success(captchaVO);
    }

    @ApiOperation("登出接口")
    @PostMapping("/logout")
    public Result<Void> logout() {
        commonLoginService.logout();
        return Result.success();
    }
}
