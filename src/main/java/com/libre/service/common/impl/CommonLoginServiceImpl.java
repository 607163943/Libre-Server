package com.libre.service.common.impl;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.aliyun.captcha20230305.Client;
import com.aliyun.captcha20230305.models.VerifyIntelligentCaptchaRequest;
import com.aliyun.captcha20230305.models.VerifyIntelligentCaptchaResponse;
import com.aliyun.teaopenapi.models.Config;
import com.libre.constant.UserState;
import com.libre.enums.ExceptionEnums;
import com.libre.exception.LoginException;
import com.libre.exception.RegisterException;
import com.libre.pojo.dto.common.*;
import com.libre.pojo.po.User;
import com.libre.pojo.po.UserRole;
import com.libre.pojo.vo.common.CaptchaVO;
import com.libre.pojo.vo.common.LoginVO;
import com.libre.service.common.CommonLoginService;
import com.libre.service.common.CommonSendMessageService;
import com.libre.service.common.CommonUserRoleService;
import com.libre.service.common.CommonUserService;
import com.libre.util.CacheUtil;
import com.libre.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommonLoginServiceImpl implements CommonLoginService {
    private final CommonUserService userService;

    private final CommonUserRoleService userRoleService;

    private final CommonSendMessageService sendMessageService;

    private final SecurityUtil securityUtil;


    private final CacheUtil cacheUtil;

    @Value("${captcha.accessKey}")
    private String accessKey;

    @Value("${captcha.secretKey}")
    private String secret;

    @Value("${captcha.sceneId}")
    private String sceneId;

    private static final String CAPTCHA_KEY_PREFIX = "captcha:key:";

    /**
     * 登录
     *
     * @param loginDTO 登录参数
     */
    @Override
    public LoginVO login(LoginDTO loginDTO, boolean isAdmin) {
        User user = userService.lambdaQuery()
                .eq(User::getUsername, loginDTO.getUsername())
                .one();
        // 检查用户账号合法性
        checkUser(user);

        // 检查密码是否正确
        if (!securityUtil.checkPassword(loginDTO.getPassword(), user.getPassword())) {
            throw new LoginException(ExceptionEnums.LOGIN_PASSWORD_ERROR);
        }

        // 后台需要管理员或者超管才能访问
        if (isAdmin) {
            List<UserRole> userRoleList = userRoleService.lambdaQuery()
                    .eq(UserRole::getUserId, user.getId())
                    .list();

            List<Long> roleIds = userRoleList.stream().map(UserRole::getRoleId).collect(Collectors.toList());

            // 2. 判断是否包含 超管(1L) 或 管理员(2L)
            boolean hasPermission = roleIds.stream().anyMatch(id ->
                    com.libre.enums.UserRole.SUPER_ADMIN.getId().equals(id) ||
                            com.libre.enums.UserRole.ADMIN.getId().equals(id)
            );

            if(!hasPermission) {
                throw new LoginException(ExceptionEnums.LOGIN_USER_NOT_ADMIN);
            }

        }

        // 更新登录时间
        user.setLastLoginTime(LocalDateTime.now());
        userService.updateById(user);

        StpUtil.login(user.getId());

        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();

        return LoginVO.builder()
                .name(user.getName())
                .tokenName(tokenInfo.getTokenName())
                .tokenValue(tokenInfo.getTokenValue())
                .build();
    }

    /**
     * 检查用户账号合法性
     *
     * @param user 用户对象
     */
    private void checkUser(User user) {
        // 检查用户是否存在
        if (user == null) {
            throw new LoginException(ExceptionEnums.LOGIN_USER_NOT_EXIST);
        }

        // 检查账号是否被禁用
        if (user.getState().equals(UserState.DISABLE)) {
            throw new LoginException(ExceptionEnums.LOGIN_USER_DISABLE);
        }
    }

    /**
     * 用户注册
     *
     * @param registerDTO 注册信息
     */
    @Transactional
    @Override
    public void register(RegisterDTO registerDTO) {
        // 检查注册用户是否存在
        Long userCount = userService.lambdaQuery()
                .eq(User::getUsername, registerDTO.getUsername())
                .count();
        if (userCount > 0) {
            throw new RegisterException(ExceptionEnums.LOGIN_REGISTER_USER_EXIST);
        }
        // 检查验证码完整性
        checkCaptcha(registerDTO.getCaptchaKey(), registerDTO.getCaptchaCode());

        User user = BeanUtil.copyProperties(registerDTO, User.class);
        // 密码加密
        user.setPassword(securityUtil.generatePassword(registerDTO.getPassword()));
        // 首次注册使用用户名代替姓名
        user.setName(registerDTO.getUsername());
        userService.save(user);

        // 首次注册用户拥有读者角色
        addUserRole(user.getId());
    }

    /**
     * 添加新用户角色
     *
     * @param userId 用户id
     */
    public void addUserRole(Long userId) {
        UserRole userRole = UserRole.builder()
                .userId(userId)
                .roleId(com.libre.enums.UserRole.READER.getId())
                .build();

        userRoleService.save(userRole);
    }

    /**
     * 检查验证码
     *
     * @param captchaKey  验证码key
     * @param captchaCode 验证码
     */
    private void checkCaptcha(String captchaKey, String captchaCode) {
        // 获取验证码
        String key = CAPTCHA_KEY_PREFIX + captchaKey;
        String code = cacheUtil.get(key);
        if (code == null || !code.equalsIgnoreCase(captchaCode)) {
            throw new RegisterException(ExceptionEnums.LOGIN_REGISTER_CAPTCHA_ERROR);
        }

        // 验证通过后删除此验证码
        cacheUtil.delete(key);
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
     *
     * @param captchaDTO 验证码DTO
     * @return 验证码
     */
    @Override
    public CaptchaVO getCaptcha(CaptchaDTO captchaDTO) {
        // 更换图片时前端会带上旧key
        if (StrUtil.isNotBlank(captchaDTO.getCaptchaKey())) {
            cacheUtil.delete(CAPTCHA_KEY_PREFIX + captchaDTO.getCaptchaKey());
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

    /**
     * 获取手机验证码
     *
     * @param captchaByPhoneDTO 手机验证码DTO
     * @return 手机验证码key
     */
    @Override
    public String getPhoneCaptcha(CaptchaByPhoneDTO captchaByPhoneDTO) {
        // 校验图形验证码合法性
        if (!checkSlideCaptcha(captchaByPhoneDTO.getCaptchaVerifyParam())) {
            throw new LoginException(ExceptionEnums.LOGIN_CAPTCHA_ERROR);
        }
        // 更新验证码则删除旧验证码
        if (StrUtil.isNotBlank(captchaByPhoneDTO.getCaptchaKey())) {
            cacheUtil.delete(CAPTCHA_KEY_PREFIX + captchaByPhoneDTO.getCaptchaKey());
        }
        // 生成6位数验证码
        String captchaCode = RandomStringUtils.randomNumeric(6, 6);

        // 发送短信
        List<String> phoneList = org.elasticsearch.core.List.of(captchaByPhoneDTO.getPhone());
        sendMessageService.sendLoginSms(phoneList, captchaCode, 1);
        // 3. 生成唯一标识符 uuid
        String uuid = IdUtil.fastSimpleUUID();
        String redisKey = CAPTCHA_KEY_PREFIX + uuid;
        // 4. 存入 Redis，设置 2 分钟有效期
        cacheUtil.set(redisKey, captchaCode, 1, TimeUnit.MINUTES);
        return uuid;
    }

    /**
     * 手机登录
     *
     * @param loginByPhoneDTO 手机登录信息
     * @return 登录信息
     */
    @Override
    public LoginVO loginByPhone(LoginByPhoneDTO loginByPhoneDTO) {
        // 检查验证码完整性
        checkCaptcha(loginByPhoneDTO.getCaptchaKey(), loginByPhoneDTO.getCaptchaCode());

        // 根据手机号查询用户
        User user = userService.lambdaQuery()
                .eq(User::getPhone, loginByPhoneDTO.getPhone())
                .one();

        // 用户存在但被禁用账号
        if (user != null && user.getState().equals(UserState.DISABLE)) {
            throw new LoginException(ExceptionEnums.LOGIN_USER_DISABLE);
        }

        // 用户存在走登录流程
        if (user != null) {
            user.setLastLoginTime(LocalDateTime.now());
            userService.updateById(user);
        }

        // 用户不存在走注册流程并自动登录
        if (user == null) {
            // 创建账号
            user = User.builder()
                    .username(loginByPhoneDTO.getPhone())
                    // 生成未知密码作为非空占位，后期用户想走密码登录通过找回密码重新设置密码
                    .password(createPhoneRegisterPassword())
                    .phone(loginByPhoneDTO.getPhone())
                    .state(UserState.NORMAL)
                    .name(loginByPhoneDTO.getPhone())
                    .lastLoginTime(LocalDateTime.now())
                    .build();

            userService.save(user);

            // 首次注册用户添加读者角色
            addUserRole(user.getId());
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
     * 创建手机注册用户的密码
     *
     * @return 手机注册密码
     */
    private String createPhoneRegisterPassword() {
        // 直接使用用户手机密码+uuid作为登录密码，防止密码偶然泄露
        String frontMd5 = SecureUtil.md5(IdUtil.fastSimpleUUID());
        return securityUtil.generatePassword(frontMd5);
    }


    /**
     * 阿里云滑块检查
     */
    public Boolean checkSlideCaptcha(String captchaVerifyParam) {
        // 获取配置
        Config config = getConfig();
        Boolean captchaVerifyResult = true;
        try {
            // ====================== 2. 初始化客户端（实际生产代码中建议复用client） ======================
            Client client = new Client(config);
            // 创建APi请求
            VerifyIntelligentCaptchaRequest request = new VerifyIntelligentCaptchaRequest();
            // 本次验证的场景ID，建议传入，防止前端被篡改场景
            request.sceneId = sceneId;
            // 前端传来的验证参数 CaptchaVerifyParam
            request.captchaVerifyParam = captchaVerifyParam;

            // ====================== 3. 发起请求） ======================
            VerifyIntelligentCaptchaResponse resp = client.verifyIntelligentCaptcha(request);
            // 建议使用您系统中的日志组件，打印返回
            // 获取验证码验证结果（请注意判空），将结果返回给前端。出现异常建议认为验证通过，优先保证业务可用，然后尽快排查异常原因。
            captchaVerifyResult = resp.body.result.verifyResult;
            // 原因code
            String captchaVerifyCode = resp.body.result.verifyCode;
            log.info("验证结果：{}，原因：{}", captchaVerifyResult, captchaVerifyCode);
        } catch (Exception error) {
            log.error("验证码验证异常：{}", error.getMessage(), error);
            throw new LoginException(ExceptionEnums.LOGIN_CAPTCHA_ERROR);
        }

        return captchaVerifyResult;
    }

    /**
     * 获取阿里云配置
     *
     * @return 阿里云配置
     */
    private Config getConfig() {
        // ====================== 1. 初始化配置 ======================
        Config config = new Config();
        // 设置您的AccessKey ID 和 AccessKey Secret。
        // getEnvProperty只是个示例方法，需要您自己实现AccessKey ID 和 AccessKey Secret安全的获取方式。
        config.accessKeyId = accessKey;
        config.accessKeySecret = secret;
        config.endpoint = "captcha.cn-shanghai.aliyuncs.com";
        // 设置连接超时为5000毫秒
        config.connectTimeout = 5000;
        // 设置读超时为5000毫秒
        config.readTimeout = 5000;

        return config;
    }


}
