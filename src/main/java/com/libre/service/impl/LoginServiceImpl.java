package com.libre.service.impl;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import com.libre.enums.ExceptionEnums;
import com.libre.exception.LoginException;
import com.libre.exception.RegisterException;
import com.libre.pojo.dto.LoginDTO;
import com.libre.pojo.dto.RegisterDTO;
import com.libre.pojo.po.User;
import com.libre.pojo.vo.LoginVO;
import com.libre.service.LoginService;
import com.libre.service.UserService;
import com.libre.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LoginServiceImpl implements LoginService {
    private final UserService userService;

    private final SecurityUtil securityUtil;

    /**
     * 登录
     * @param loginDTO 登录参数
     */
    @Override
    public LoginVO login(LoginDTO loginDTO) {
        User user = userService.lambdaQuery()
                .eq(User::getUsername, loginDTO.getUsername())
                .one();
        // 检查用户是否存在
        if(user==null) {
            throw new LoginException(ExceptionEnums.LOGIN_USER_NOT_EXIST);
        }

        // 检查密码是否正确
        if(!securityUtil.checkPassword(loginDTO.getPassword(), user.getPassword())) {
            throw new LoginException(ExceptionEnums.LOGIN_PASSWORD_ERROR);
        }

        StpUtil.login(user.getId());

        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();

        return LoginVO.builder()
                .name(user.getName())
                .tokenName(tokenInfo.getTokenName())
                .tokenValue(tokenInfo.getTokenValue())
                .build();
    }

    /**
     *  用户注册
     * @param registerDTO 注册信息
     */
    @Override
    public void register(RegisterDTO registerDTO) {
        Long userCount = userService.lambdaQuery()
                .eq(User::getUsername, registerDTO.getUsername())
                .count();
        if(userCount>0) {
            throw new RegisterException(ExceptionEnums.LOGIN_REGISTER_USER_EXIST);
        }

        User user = BeanUtil.copyProperties(registerDTO, User.class);
        // 密码加密
        user.setPassword(securityUtil.generatePassword(registerDTO.getPassword()));
        // 首次注册使用用户名代替姓名
        user.setName(registerDTO.getUsername());
        userService.save(user);
    }

    /**
     * 登出
     */
    @Override
    public void logout() {
        StpUtil.logout();
    }
}
