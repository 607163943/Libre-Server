package com.libre.service.impl;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.libre.enums.ExceptionEnums;
import com.libre.exception.LoginException;
import com.libre.pojo.dto.LoginDTO;
import com.libre.pojo.po.User;
import com.libre.pojo.vo.LoginVO;
import com.libre.service.LoginService;
import com.libre.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LoginServiceImpl implements LoginService {
    private final UserService userService;

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
            throw new LoginException(ExceptionEnums.USER_NOT_EXIST);
        }

        // 检查密码是否正确
        if(!user.getPassword().equals(loginDTO.getPassword())) {
            throw new LoginException(ExceptionEnums.PASSWORD_ERROR);
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
     * 登出
     */
    @Override
    public void logout() {
        StpUtil.logout();
    }
}
