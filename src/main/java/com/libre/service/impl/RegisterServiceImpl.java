package com.libre.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.crypto.digest.BCrypt;
import com.libre.enums.ExceptionEnums;
import com.libre.exception.RegisterException;
import com.libre.pojo.dto.RegisterDTO;
import com.libre.pojo.po.User;
import com.libre.service.RegisterService;
import com.libre.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RegisterServiceImpl implements RegisterService {
    private final UserService userService;

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
            throw new RegisterException(ExceptionEnums.REGISTER_USER_EXIST);
        }

        User user = BeanUtil.copyProperties(registerDTO, User.class);
        // 密码加密
        user.setPassword(BCrypt.hashpw(registerDTO.getPassword()));
        // 首次注册使用用户名代替姓名
        user.setName(registerDTO.getUsername());
        userService.save(user);
    }
}
