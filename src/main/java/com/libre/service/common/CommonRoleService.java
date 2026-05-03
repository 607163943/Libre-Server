package com.libre.service.common;

import com.baomidou.mybatisplus.extension.service.IService;
import com.libre.pojo.po.Role;

public interface CommonRoleService extends IService<Role> {
    /**
     * 初始化角色数据
     */
    void initRole();
}
