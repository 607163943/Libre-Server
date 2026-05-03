package com.libre.service.admin;

import com.baomidou.mybatisplus.extension.service.IService;
import com.libre.pojo.po.Role;

import java.util.List;

public interface AdminRoleService extends IService<Role> {

    /**
     * 获取所有角色列表（带缓存）
     * @return 角色列表
     */
    List<Role> getAllRole();
}
