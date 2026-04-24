package com.libre.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.libre.pojo.dto.PermissionDTO;
import com.libre.pojo.dto.PermissionPageDTO;
import com.libre.pojo.po.Permission;
import com.libre.pojo.vo.PermissionPageVO;
import com.libre.result.PageResult;

import java.util.List;

public interface PermissionService extends IService<Permission> {
    /**
     * 分页查询权限信息
     * @param permissionPageDTO 查询参数
     * @return 查询结果
     */
    PageResult<List<PermissionPageVO>> pageQueryPermission(PermissionPageDTO permissionPageDTO);

    /**
     * 添加权限
     * @param permissionDTO 权限信息
     */
    void addPermission(PermissionDTO permissionDTO);

    /**
     * 修改权限
     * @param permissionDTO 权限信息
     */
    void modifyPermission(PermissionDTO permissionDTO);

    /**
     * 删除权限
     * @param permissionId 权限id
     */
    void deletePermission(Long permissionId);

    /**
     * 批量删除权限
     * @param ids 权限id集合
     */
    void deleteBatchPermission(List<Long> ids);

    /**
     * 获取所有权限列表（带缓存）
     * @return 权限列表
     */
    List<Permission> getAllPermission();
}
