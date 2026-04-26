package com.libre.service.admin.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.libre.mapper.RolePermissionMapper;
import com.libre.pojo.dto.admin.AddRolePermissionDTO;
import com.libre.pojo.po.RolePermission;
import com.libre.service.admin.AdminRolePermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AdminRolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermission> implements AdminRolePermissionService {
    // 处理自调用事务问题
    @Lazy
    @Resource
    private AdminRolePermissionService adminRolePermissionService;

    @Override
    public List<Long> getRolePermissionIds(Long roleId) {
        List<RolePermission> rolePermissionList = lambdaQuery()
                .eq(RolePermission::getRoleId, roleId)
                .list();

        return rolePermissionList.stream()
                .map(RolePermission::getPermissionId)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void assignRolePermission(AddRolePermissionDTO addRolePermissionDTO) {
        // 删除旧权限
        adminRolePermissionService.lambdaUpdate()
                .set(RolePermission::getIsDelete, System.currentTimeMillis())
                .eq(RolePermission::getRoleId, addRolePermissionDTO.getRoleId())
                .update();

        // 添加新权限
        // 构建角色权限集合
        List<RolePermission> rolePermissionList = new ArrayList<>(addRolePermissionDTO.getPermissionIds().size());

        for (Long permissionId : addRolePermissionDTO.getPermissionIds()) {
            rolePermissionList.add(new RolePermission(addRolePermissionDTO.getRoleId(), permissionId));
        }

        // 批量插入
        adminRolePermissionService.saveBatch(rolePermissionList);
    }
}
