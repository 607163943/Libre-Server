package com.libre.service;

import com.libre.pojo.dto.LoginDTO;

public interface LoginService {
    /**
     * 登录
     * @param loginDTO 登录参数
     */
    void login(LoginDTO loginDTO);
}
