package com.libre.service.common.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.libre.constant.RoleCode;
import com.libre.enums.ExceptionEnums;
import com.libre.exception.UserException;
import com.libre.mapper.UserMapper;
import com.libre.pojo.dto.common.UserPasswordDTO;
import com.libre.pojo.dto.common.UserProfileDTO;
import com.libre.pojo.po.User;
import com.libre.pojo.po.UserRole;
import com.libre.pojo.vo.common.UserProfileVO;
import com.libre.service.common.CommonUserRoleService;
import com.libre.service.common.CommonUserService;
import com.libre.util.CacheUtil;
import com.libre.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommonUserServiceImpl extends ServiceImpl<UserMapper, User> implements CommonUserService {
    private final CommonUserRoleService userRoleService;
    private final SecurityUtil securityUtil;
    private final CacheUtil cacheUtil;

    @Lazy
    @Resource
    private CommonUserService userService;

    @Transactional
    @Override
    public void initSuperAdmin() {
        // 1. 检查是否存在超管角色关联账户
        long count = userRoleService.lambdaQuery()
                .eq(UserRole::getRoleId, RoleCode.SUPER_ADMIN)
                .count();

        if (count != 0) {
            return;
        }

        // 检查是否存在admin账户
        User user = lambdaQuery()
                .eq(User::getUsername, "admin")
                .one();

        log.warn("检测到系统中无超管账号，准备初始化超管账号...");

        // 无admin账户
        if(user==null) {
            String md5String = SecureUtil.md5("123456");
            String passwordString = securityUtil.generatePassword(md5String);

            // 5. 写入数据库
            user = new User();
            user.setUsername("admin");
            user.setNickName("admin");
            user.setPassword(passwordString);

            userService.save(user);
        }

        log.warn("检测到系统中已存在admin账户，建立初始化角色关联");
        UserRole userRole = new UserRole(user.getId(), com.libre.enums.UserRole.SUPER_ADMIN.getId());
        userRoleService.save(userRole);

        log.info("初始化成功");
    }

    /**
     * 获取当前用户个人信息
     * @return 用户个人信息
     */
    @Override
    public UserProfileVO getUserProfile() {
        Long userId = StpUtil.getLoginIdAsLong();
        String cacheKey = "user:profile:" + userId;

        // 尝试从缓存中获取
        String cachedData = cacheUtil.get(cacheKey);
        if (cachedData != null) {
            return JSONUtil.toBean(cachedData, UserProfileVO.class);
        }

        // 缓存未命中，查询数据库
        User user = getById(userId);
        if (user == null) {
            throw new UserException(ExceptionEnums.USER_NOT_EXIST);
        }
        UserProfileVO result = BeanUtil.copyProperties(user, UserProfileVO.class);

        // 存入缓存，过期时间30分钟
        cacheUtil.set(cacheKey, JSONUtil.toJsonStr(result), 30, TimeUnit.MINUTES);

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
            throw new UserException(ExceptionEnums.USER_NOT_EXIST);
        }

        // 更新姓名
        if (StrUtil.isNotBlank(userProfileDTO.getName())) {
            user.setNickName(userProfileDTO.getName());
        }

        // 更新邮箱
        if(StrUtil.isNotBlank(userProfileDTO.getEmail())) {
            // 查看邮箱是否已绑定
            Long emailCount = lambdaQuery()
                    .eq(User::getEmail, userProfileDTO.getEmail())
                    .ne(User::getId, userId)
                    .count();

            if(emailCount>0) {
                throw new UserException(ExceptionEnums.USER_EMAIL_ALREADY_EXIST);
            }

            user.setEmail(userProfileDTO.getEmail());
        }

        // 更新手机号
        // 更新邮箱
        if(StrUtil.isNotBlank(userProfileDTO.getPhone())) {
            // 查看邮箱是否已绑定
            Long phoneCount = lambdaQuery()
                    .eq(User::getPhone, userProfileDTO.getPhone())
                    .ne(User::getId, userId)
                    .count();

            if(phoneCount>0) {
                throw new UserException(ExceptionEnums.USER_PHONE_ALREADY_EXIST);
            }

            user.setPhone(userProfileDTO.getPhone());
        }
        updateById(user);

        // 清除缓存
        String cacheKey = "user:profile:" + userId;
        cacheUtil.delete(cacheKey);
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
            throw new UserException(ExceptionEnums.USER_NOT_EXIST);
        }

        // 校验旧密码
        if (!securityUtil.checkPassword(userPasswordDTO.getOldPassword(), user.getPassword())) {
            throw new UserException(ExceptionEnums.LOGIN_PASSWORD_ERROR);
        }

        // 更新新密码
        user.setPassword(securityUtil.generatePassword(userPasswordDTO.getNewPassword()));
        updateById(user);
    }
}
