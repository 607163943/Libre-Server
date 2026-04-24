package com.libre.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.libre.pojo.dto.RolePermissionDTO;
import com.libre.pojo.dto.RolePermissionPageDTO;
import com.libre.pojo.po.RolePermission;
import com.libre.pojo.vo.RolePermissionPageVO;
import com.libre.result.PageResult;

import java.util.List;

public interface RolePermissionService extends IService<RolePermission> {
    /**
     * 分页查询角色权限关联信息
     * @param rolePermissionPageDTO 查询参数
     * @return 查询结果
     */
    PageResult<List<RolePermissionPageVO>> pageQueryRolePermission(RolePermissionPageDTO rolePermissionPageDTO);

    /**
     * 添加角色权限关联
     * @param rolePermissionDTO 角色权限关联信息
     */
    void addRolePermission(RolePermissionDTO rolePermissionDTO);

    /**
     * 修改角色权限关联
     * @param rolePermissionDTO 角色权限关联信息
     */
    void modifyRolePermission(RolePermissionDTO rolePermissionDTO);

    /**
     * 删除角色权限关联
     * @param id 记录id
     */
    void deleteRolePermission(Long id);

    /**
     * 批量删除角色权限关联
     * @param ids 记录id集合
     */
    void deleteBatchRolePermission(List<Long> ids);
}
