package com.libre.service.admin.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.libre.constant.RoleCode;
import com.libre.enums.ExceptionEnums;
import com.libre.exception.UserException;
import com.libre.mapper.UserRoleMapper;
import com.libre.pojo.dto.admin.AddUserRoleDTO;
import com.libre.pojo.po.UserRole;
import com.libre.pojo.vo.admin.RoleVO;
import com.libre.service.admin.AdminUserRoleService;
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
public class AdminUserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements AdminUserRoleService {
    // 解决自盗用导致的事务失效问题
    @Lazy
    @Resource
    private AdminUserRoleService adminUserRoleService;


    /**
     * 获取用户角色信息
     * @param userId 用户id
     * @return 用户角色信息
     */
    @Override
    public List<RoleVO> getUserRoles(Long userId) {
        return baseMapper.getUserRoles(userId);
    }

    /**
     * 添加用户角色信息
     * @param addUserRoleDTO 添加用户角色信息
     */
    @Transactional
    @Override
    public void assignUserRoles(AddUserRoleDTO addUserRoleDTO) {
        Long targetUserId = addUserRoleDTO.getUserId();
        List<Long> newRoleIds = addUserRoleDTO.getRoleIds();
        
        // 获取目标用户当前的角色列表
        List<RoleVO> targetUserOldRoles = getUserRoles(targetUserId);
        List<Long> oldRoleIds = targetUserOldRoles.stream()
                .map(RoleVO::getId)
                .collect(Collectors.toList());
        
        // 校验删除旧角色的权限
        checkRoleUpdatePermission(oldRoleIds);

        // 校验分配新角色的权限
        checkRoleUpdatePermission(newRoleIds);
        
        // 删除用户旧角色信息
        adminUserRoleService.lambdaUpdate()
                .set(UserRole::getIsDelete, System.currentTimeMillis())
                .eq(UserRole::getUserId, targetUserId)
                .update();
        
        // 构建UserRole实体集合
        List<UserRole> userRoleList = new ArrayList<>(newRoleIds.size());
        for (Long roleId : newRoleIds) {
            userRoleList.add(new UserRole(targetUserId, roleId));
        }

        // 批量添加
        adminUserRoleService.saveBatch(userRoleList);
    }

    /**
     * 校验分配用户角色的权限
     * 规则：
     * - 超管能给用户分配读者和管理员角色
     * - 管理员能给用户分配读者角色
     * - 读者不能给任何用户分配角色
     * @param roleIds 要分配的角色ID列表
     */
    private void checkRoleUpdatePermission(List<Long> roleIds) {
        // 如果要分配的角色列表为空，直接返回
        if (roleIds == null || roleIds.isEmpty()) {
            return;
        }
        
        // 获取当前登录用户ID
        Long currentUserId = StpUtil.getLoginIdAsLong();
        
        // 获取当前登录用户的角色列表
        List<RoleVO> currentUserRoles = getUserRoles(currentUserId);
        List<Long> currentUserRoleIds = currentUserRoles.stream()
                .map(RoleVO::getId)
                .collect(Collectors.toList());

        // 判断当前用户是否有权限分配角色
        boolean hasPermission = false;

        // 超级管理员(1)可以分配读者(3)和管理员(2)角色
        if (currentUserRoleIds.contains(RoleCode.SUPER_ADMIN.longValue())) {
            hasPermission = true;
        }
        // 管理员(2)只能分配读者(3)角色
        else if (currentUserRoleIds.contains(RoleCode.ADMIN.longValue())) {
            // 检查要分配的角色是否都是读者角色
            boolean allReaderRoles = roleIds.stream()
                    .allMatch(roleId -> roleId.equals(RoleCode.READER.longValue()));
            if (allReaderRoles) {
                hasPermission = true;
            }
        }
        // 读者(3)不能分配任何角色

        if (!hasPermission) {
            throw new UserException(ExceptionEnums.USER_PERMISSION_DENIED);
        }
    }
}
