package com.libre.service.common;

import com.baomidou.mybatisplus.extension.service.IService;
import com.libre.pojo.po.Permission;

import java.util.List;

public interface CommonPermissionService extends IService<Permission> {
    /**
     * 获取权限码列表
     * @param permissionIds 权限id集合
     * @return 权限码列表
     */
    List<String> getPermissionCodes(List<Long> permissionIds);
}
