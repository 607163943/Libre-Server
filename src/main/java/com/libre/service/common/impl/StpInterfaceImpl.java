package com.libre.service.common.impl;

import cn.dev33.satoken.stp.StpInterface;
import cn.hutool.core.collection.CollUtil;
import com.libre.pojo.po.Role;
import com.libre.pojo.po.RolePermission;
import com.libre.pojo.po.UserRole;
import com.libre.service.common.CommonPermissionService;
import com.libre.service.common.CommonRolePermissionService;
import com.libre.service.common.CommonRoleService;
import com.libre.service.common.CommonUserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 自定义权限加载接口实现类
 */
@RequiredArgsConstructor
@Service    // 保证此类被 SpringBoot 扫描，完成 Sa-Token 的自定义权限验证扩展
public class StpInterfaceImpl implements StpInterface {
    private final CommonRoleService roleService;

    private final CommonUserRoleService userRoleService;

    private final CommonPermissionService permissionService;

    private final CommonRolePermissionService rolePermissionService;

    /**
     * 返回一个账号所拥有的权限码集合
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        // 查询用户拥有角色id集合
        List<UserRole> userRoleList = userRoleService.lambdaQuery()
                .eq(UserRole::getUserId, loginId)
                .list();
        if(CollUtil.isEmpty(userRoleList)) {
            return Collections.emptyList();
        }
        List<Long> roleIds = userRoleList.stream().map(UserRole::getRoleId).collect(Collectors.toList());

        // 查询角色拥有权限id集合
        List<RolePermission> rolePermissionList = rolePermissionService.lambdaQuery()
                .in(RolePermission::getRoleId, roleIds)
                .list();
        if(CollUtil.isEmpty(rolePermissionList)) {
            return Collections.emptyList();
        }
        List<Long> permissionIds = rolePermissionList.stream().map(RolePermission::getPermissionId).collect(Collectors.toList());

        // 获取权限码集合
        List<String> permissionCodes = permissionService.getPermissionCodes(permissionIds);

        return permissionCodes.stream()
                // 集合去重
                .distinct()
                .collect(Collectors.toList());
    }

    /**
     * 返回一个账号所拥有的角色标识集合 (权限与角色可分开校验)
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        // 查询用户拥有角色id集合
        List<UserRole> userRoleList = userRoleService.lambdaQuery()
                .eq(UserRole::getUserId, loginId)
                .list();

        // 查询角色
        List<Long> roleIds = userRoleList.stream()
                .map(UserRole::getRoleId)
                .collect(Collectors.toList());
        List<Role> roleList = roleService.lambdaQuery()
                .in(Role::getId, roleIds)
                .list();

        // 提取角色名
        return roleList.stream()
                .map(Role::getRoleName)
                .collect(Collectors.toList());
    }

}
