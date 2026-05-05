package com.libre.service.common.impl;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.libre.constant.UserState;
import com.libre.enums.ExceptionEnums;
import com.libre.exception.LoginException;
import com.libre.exception.RegisterException;
import com.libre.pojo.dto.common.CaptchaDTO;
import com.libre.pojo.dto.common.LoginDTO;
import com.libre.pojo.dto.common.RegisterDTO;
import com.libre.pojo.po.User;
import com.libre.pojo.vo.common.CaptchaVO;
import com.libre.pojo.vo.common.LoginVO;
import com.libre.service.common.CommonLoginService;
import com.libre.service.common.CommonUserService;
import com.libre.util.CacheUtil;
import com.libre.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Service
public class CommonLoginServiceImpl implements CommonLoginService {
    private final CommonUserService commonUserService;

    private final SecurityUtil securityUtil;


    private final CacheUtil cacheUtil;

    private static final String CAPTCHA_KEY_PREFIX = "captcha:key:";
    /**
     * 登录
     * @param loginDTO 登录参数
     */
    @Override
    public LoginVO login(LoginDTO loginDTO) {
        User user = commonUserService.lambdaQuery()
                .eq(User::getUsername, loginDTO.getUsername())
                .one();
        // 检查用户是否存在
        if(user==null) {
            throw new LoginException(ExceptionEnums.LOGIN_USER_NOT_EXIST);
        }

        // 检查账号是否被禁用
        if(user.getState().equals(UserState.DISABLE)) {
            throw new LoginException(ExceptionEnums.LOGIN_USER_DISABLE);
        }

        // 检查密码是否正确
        if(!securityUtil.checkPassword(loginDTO.getPassword(), user.getPassword())) {
            throw new LoginException(ExceptionEnums.LOGIN_PASSWORD_ERROR);
        }

        // 更新登录时间
        user.setLastLoginTime(LocalDateTime.now());
        commonUserService.updateById(user);

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
        // 检查注册用户是否存在
        Long userCount = commonUserService.lambdaQuery()
                .eq(User::getUsername, registerDTO.getUsername())
                .count();
        if(userCount>0) {
            throw new RegisterException(ExceptionEnums.LOGIN_REGISTER_USER_EXIST);
        }
        // 检查验证码完整性
        checkCaptcha(registerDTO);

        User user = BeanUtil.copyProperties(registerDTO, User.class);
        // 密码加密
        user.setPassword(securityUtil.generatePassword(registerDTO.getPassword()));
        // 首次注册使用用户名代替姓名
        user.setName(registerDTO.getUsername());
        commonUserService.save(user);
    }

    private void checkCaptcha(RegisterDTO registerDTO) {
        // 获取验证码
        String key = CAPTCHA_KEY_PREFIX + registerDTO.getCaptchaKey();
        String code = cacheUtil.get(key);
        if(code==null||code.equalsIgnoreCase(registerDTO.getCaptchaCode())) {
            throw new RegisterException(ExceptionEnums.LOGIN_REGISTER_CAPTCHA_ERROR);
        }
    }

    /**
     * 登出
     */
    @Override
    public void logout() {
        StpUtil.logout();
    }

    /**
     * 获取验证码
     * @param captchaDTO 验证码DTO
     * @return 验证码
     */
    @Override
    public CaptchaVO getCaptcha(CaptchaDTO captchaDTO) {
        // 更换图片时前端会带上旧key
        if(StrUtil.isNotBlank(captchaDTO.getCaptchaKey())) {
            cacheUtil.delete(CAPTCHA_KEY_PREFIX+captchaDTO.getCaptchaKey());
        }
        // 1. 定义图形验证码的长和宽 (可以根据前端界面微调)
        // LineCaptcha 线条干扰，CircleCaptcha 圆圈干扰，ShearCaptcha 扭曲干扰
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(100, 40, 4, 10);

        // 2. 获取验证码内容（如：abcd）
        String code = lineCaptcha.getCode();

        // 3. 生成唯一标识符 uuid
        String uuid = IdUtil.fastSimpleUUID();
        String redisKey = CAPTCHA_KEY_PREFIX + uuid;

        // 4. 存入 Redis，设置 2 分钟有效期
        cacheUtil.set(redisKey, code, 1, TimeUnit.MINUTES);

        // 5. 返回结果：uuid 用于后续校验，Base64 用于前端展示
        return CaptchaVO.builder()
                .captchaImgBase64(lineCaptcha.getImageBase64Data())
                .captchaKey(uuid)
                .build();

    }
}
