package com.libre.service.admin;

import com.baomidou.mybatisplus.extension.service.IService;
import com.libre.pojo.dto.RolePermissionDTO;
import com.libre.pojo.dto.RolePermissionPageDTO;
import com.libre.pojo.dto.admin.AddRolePermissionDTO;
import com.libre.pojo.po.RolePermission;
import com.libre.pojo.vo.RolePermissionPageVO;
import com.libre.result.PageResult;

import java.util.List;

public interface AdminRolePermissionService extends IService<RolePermission> {
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
