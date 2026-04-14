package com.libre.service;

import com.libre.pojo.dto.RegisterDTO;

public interface RegisterService {
    /**
     *  用户注册
     * @param registerDTO 注册信息
     */
    void register(RegisterDTO registerDTO);
}
