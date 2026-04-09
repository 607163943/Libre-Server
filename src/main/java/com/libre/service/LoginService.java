package com.libre.service;

import com.libre.pojo.dto.LoginDTO;
import com.libre.pojo.vo.LoginVO;

public interface LoginService {
    /**
     * 登录
     * @param loginDTO 登录参数
     */
    LoginVO login(LoginDTO loginDTO);

    /**
     * 登出
     */
    void logout();
}
