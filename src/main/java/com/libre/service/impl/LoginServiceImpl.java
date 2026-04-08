package com.libre.service.impl;

import com.libre.enums.ExceptionEnums;
import com.libre.exception.LoginException;
import com.libre.pojo.dto.LoginDTO;
import com.libre.pojo.po.User;
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
    public void login(LoginDTO loginDTO) {
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
    }
}
