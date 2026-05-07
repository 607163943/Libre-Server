package com.libre.service.common;

import com.libre.pojo.dto.common.*;
import com.libre.pojo.vo.common.CaptchaVO;
import com.libre.pojo.vo.common.LoginVO;

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

    /**
     * 获取验证码
     * @param captchaDTO 验证码DTO
     * @return 验证码
     */
    CaptchaVO getCaptcha(CaptchaDTO captchaDTO);

    /**
     * 获取手机验证码
     * @param captchaByPhoneDTO 手机验证码DTO
     * @return 手机验证码
     */
    String getPhoneCaptcha(CaptchaByPhoneDTO captchaByPhoneDTO);

    /**
     * 手机登录
     * @param loginByPhoneDTO 手机登录信息
     * @return 登录信息
     */
    LoginVO loginByPhone(LoginByPhoneDTO loginByPhoneDTO);
}
