package com.libre.service.impl;

import cn.dev33.satoken.stp.StpInterface;
import com.libre.pojo.po.Role;
import com.libre.pojo.po.UserRole;
import com.libre.service.RoleService;
import com.libre.service.UserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 自定义权限加载接口实现类
 */
@RequiredArgsConstructor
@Service    // 保证此类被 SpringBoot 扫描，完成 Sa-Token 的自定义权限验证扩展
public class StpInterfaceImpl implements StpInterface {
    private final RoleService roleService;

    private final UserRoleService userRoleService;

    /**
     * 返回一个账号所拥有的权限码集合
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        // 本 list 仅做模拟，实际项目中要根据具体业务逻辑来查询权限
        List<String> list = new ArrayList<String>();
        list.add("101");
        list.add("user.add");
        list.add("user.update");
        list.add("user.get");
        // list.add("user.delete");
        list.add("art.*");
        return list;
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
