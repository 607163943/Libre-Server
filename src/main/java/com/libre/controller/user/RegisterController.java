package com.libre.controller.user;

import com.libre.pojo.dto.RegisterDTO;
import com.libre.result.Result;
import com.libre.service.RegisterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "用户端注册接口")
@RequiredArgsConstructor
@RequestMapping("/register")
@RestController("user-register")
public class RegisterController {
    private final RegisterService registerService;

    @ApiOperation("用户注册")
    @PostMapping
    public Result<Void> register(@RequestBody RegisterDTO registerDTO) {
        registerService.register(registerDTO);
        return Result.success();
    }
}
