package com.libre.service.admin;

import com.baomidou.mybatisplus.extension.service.IService;
import com.libre.pojo.dto.admin.AddRolePermissionDTO;
import com.libre.pojo.po.RolePermission;

import java.util.List;

public interface AdminRolePermissionService extends IService<RolePermission> {
    /**
     * 根据角色id查询角色权限关联
     * @param roleId 角色id
     * @return 角色权限关联
     */
    List<Long> getRolePermissionIds(Long roleId);

    /**
     * 添加角色权限关联
     * @param addRolePermissionDTO 角色权限关联信息
     */
    void assignRolePermission(AddRolePermissionDTO addRolePermissionDTO);
}
