package com.libre.service.common.impl;

import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.libre.constant.RoleCode;
import com.libre.mapper.UserMapper;
import com.libre.pojo.po.User;
import com.libre.pojo.po.UserRole;
import com.libre.service.common.CommonUserRoleService;
import com.libre.service.common.CommonUserService;
import com.libre.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommonUserServiceImpl extends ServiceImpl<UserMapper, User> implements CommonUserService {
    private final CommonUserRoleService userRoleService;
    private final SecurityUtil securityUtil;

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
            user.setName("admin");
            user.setPassword(passwordString);

            userService.save(user);
        }

        log.warn("检测到系统中已存在admin账户，建立初始化角色关联");
        UserRole userRole = new UserRole(user.getId(), com.libre.enums.UserRole.SUPER_ADMIN.getId());
        userRoleService.save(userRole);

        log.info("初始化成功");
    }
}
