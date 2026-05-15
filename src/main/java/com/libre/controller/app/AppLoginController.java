package com.libre.controller.app;

import com.libre.pojo.dto.common.*;
import com.libre.pojo.vo.common.CaptchaVO;
import com.libre.pojo.vo.common.LoginVO;
import com.libre.result.Result;
import com.libre.service.common.CommonLoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
        LoginVO loginVO = commonLoginService.login(loginDTO, false);
        return Result.success(loginVO);
    }

    @ApiOperation("手机号登录")
    @PostMapping("/login/phone")
    public Result<LoginVO> loginByPhone(@RequestBody @Valid LoginByPhoneDTO loginByPhoneDTO) {
        LoginVO loginVO = commonLoginService.loginByPhone(loginByPhoneDTO);
        return Result.success(loginVO);
    }

    @ApiOperation("用户注册")
    @PostMapping("/register")
    public Result<Void> register(@RequestBody @Valid RegisterDTO registerDTO) {
        commonLoginService.register(registerDTO);
        return Result.success();
    }

    @ApiOperation("获取验证码")
    @PutMapping("/register/captcha")
    public Result<CaptchaVO> getCaptcha(@RequestBody CaptchaDTO captchaDTO) {
        CaptchaVO captchaVO = commonLoginService.getCaptcha(captchaDTO);
        return Result.success(captchaVO);
    }

    @ApiOperation("获取手机短信验证码")
    @PutMapping("/login/phone/captcha")
    public Result<String> getPhoneCaptcha(@RequestBody @Valid CaptchaByPhoneDTO captchaByPhoneDTO) {
        String captchaKey = commonLoginService.getPhoneCaptcha(captchaByPhoneDTO);
        return Result.success(captchaKey);
    }

    @ApiOperation("用户登出")
    @PostMapping("/logout")
    public Result<Void> logout() {
        commonLoginService.logout();
        return Result.success();
    }
}
