package com.libre.service.app.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.libre.enums.CommonExceptionEnums;
import com.libre.exception.UserException;
import com.libre.mapper.UserMapper;
import com.libre.pojo.dto.app.UserPasswordDTO;
import com.libre.pojo.dto.app.UserProfileDTO;
import com.libre.pojo.po.User;
import com.libre.pojo.vo.app.UserProfileVO;
import com.libre.service.app.AppUserService;
import com.libre.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Service
public class AppUserServiceImpl extends ServiceImpl<UserMapper, User> implements AppUserService {
    private final SecurityUtil securityUtil;

    private final StringRedisTemplate stringRedisTemplate;

    /**
     * 获取当前用户个人信息
     *
     * @param userId 用户id
     * @return 用户个人信息
     */
    @Override
    public UserProfileVO getUserProfile(Long userId) {
        String cacheKey = "user:profile:" + userId;

        // 尝试从缓存中获取
        String cachedData = stringRedisTemplate.opsForValue().get(cacheKey);
        if (cachedData != null) {
            return JSONUtil.toBean(cachedData, UserProfileVO.class);
        }

        // 缓存未命中，查询数据库
        User user = getById(userId);
        if (user == null) {
            throw new UserException(CommonExceptionEnums.LOGIN_USER_NOT_EXIST);
        }
        UserProfileVO result = BeanUtil.copyProperties(user, UserProfileVO.class);

        // 存入缓存，过期时间30分钟
        stringRedisTemplate.opsForValue().set(cacheKey, JSONUtil.toJsonStr(result), 30, TimeUnit.MINUTES);

        return result;
    }

    /**
     * 修改当前用户个人信息
     * @param userProfileDTO 用户个人信息
     */
    @Override
    public void modifyUserProfile(UserProfileDTO userProfileDTO) {
        Long userId = StpUtil.getLoginIdAsLong();
        User user = getById(userId);
        if (user == null) {
            throw new UserException(CommonExceptionEnums.LOGIN_USER_NOT_EXIST);
        }

        // 更新姓名
        if (StrUtil.isNotBlank(userProfileDTO.getName())) {
            user.setName(userProfileDTO.getName());
        }
        updateById(user);

        // 清除缓存
        String cacheKey = "user:profile:" + userId;
        stringRedisTemplate.delete(cacheKey);
    }

    /**
     * 修改当前用户密码
     * @param userPasswordDTO 用户密码信息
     */
    @Override
    public void modifyUserPassword(UserPasswordDTO userPasswordDTO) {
        Long userId = StpUtil.getLoginIdAsLong();
        User user = getById(userId);
        if (user == null) {
            throw new UserException(CommonExceptionEnums.LOGIN_USER_NOT_EXIST);
        }

        // 校验旧密码
        if (!securityUtil.checkPassword(userPasswordDTO.getOldPassword(), user.getPassword())) {
            throw new UserException(CommonExceptionEnums.LOGIN_PASSWORD_ERROR);
        }

        // 更新新密码
        user.setPassword(securityUtil.generatePassword(userPasswordDTO.getNewPassword()));
        updateById(user);
    }
}
