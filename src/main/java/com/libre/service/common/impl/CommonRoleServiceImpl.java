package com.libre.service.common.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.libre.enums.UserRole;
import com.libre.mapper.RoleMapper;
import com.libre.pojo.po.Role;
import com.libre.service.common.CommonRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CommonRoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements CommonRoleService {
    /**
     * 初始化角色数据
     */
    @Transactional
    @Override
    public void initRole() {
        List<UserRole> userRoles = new ArrayList<>(Arrays.asList(UserRole.SUPER_ADMIN, UserRole.ADMIN, UserRole.READER));

        for (UserRole userRole : userRoles) {
            checkRole(userRole);
        }
    }

    /**
     * 检查角色
     * @param userRole 角色枚举
     */
    public void checkRole(UserRole userRole) {
        // 批量修改
        Role role = lambdaQuery()
                .eq(Role::getRoleName,userRole.getRoleName())
                .one();
        // 角色不存在
        if(role==null) {
            // 判断该UserRole指定id是否被占用
            role = lambdaQuery()
                    .eq(Role::getId, userRole.getId())
                    .one();

            if(role!=null) {
                role.setRoleName(userRole.getRoleName());
                updateById(role);
                return;
            }

            // 未被占用
            role = Role.builder()
                    .id(userRole.getId())
                    .roleName(userRole.getRoleName())
                    .build();
            save(role);
            return;
        }

        // 角色存在但id不一致
        if(!role.getId().equals(userRole.getId())) {
            role.setId(userRole.getId());
            updateById(role);
        }
    }
}
