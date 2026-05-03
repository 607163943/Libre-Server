package com.libre.service.common;

import com.baomidou.mybatisplus.extension.service.IService;
import com.libre.pojo.po.User;

public interface CommonUserService extends IService<User> {
    /**
     * 初始化超管账号
     */
    void initSuperAdmin();
}
