package com.libre.handler;

import com.libre.service.common.CommonRoleService;
import com.libre.service.common.CommonUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Slf4j
@Component
public class AdminInitializer implements ApplicationRunner {

    @Resource
    private CommonUserService userService; // 你的用户 Service


    @Resource
    private CommonRoleService roleService;

    @Transactional
    @Override
    public void run(ApplicationArguments args) {
        // 初始化角色数据
        roleService.initRole();
        // 初始化超管账号
        userService.initSuperAdmin();
    }
}