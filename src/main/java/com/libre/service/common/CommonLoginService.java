package com.libre.service.common;

import com.libre.pojo.dto.LoginDTO;
import com.libre.pojo.dto.RegisterDTO;
import com.libre.pojo.vo.LoginVO;

public interface CommonLoginService {

    /**
     * 登录
     * @param loginDTO 登录参数
     */
    LoginVO login(LoginDTO loginDTO);

    /**
     *  用户注册
     * @param registerDTO 注册信息
     */
    void register(RegisterDTO registerDTO);

    /**
     * 登出
     */
    void logout();
}
