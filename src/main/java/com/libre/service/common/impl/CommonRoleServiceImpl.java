package com.libre.service.common.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.libre.enums.UserRoleEnums;
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
        List<UserRoleEnums> userRoleEnums = new ArrayList<>(Arrays.asList(UserRoleEnums.SUPER_ADMIN, UserRoleEnums.ADMIN, UserRoleEnums.READER));

        for (UserRoleEnums userRole : userRoleEnums) {
            checkRole(userRole);
        }
    }

    /**
     * 检查角色
     * @param userRoleEnums 角色枚举
     */
    public void checkRole(UserRoleEnums userRoleEnums) {
        // 批量修改
        Role role = lambdaQuery()
                .eq(Role::getRoleName, userRoleEnums.getRoleName())
                .one();
        // 角色不存在
        if(role==null) {
            // 判断该UserRole指定id是否被占用
            role = lambdaQuery()
                    .eq(Role::getId, userRoleEnums.getId())
                    .one();

            if(role!=null) {
                role.setRoleName(userRoleEnums.getRoleName());
                updateById(role);
                return;
            }

            // 未被占用
            role = Role.builder()
                    .id(userRoleEnums.getId())
                    .roleName(userRoleEnums.getRoleName())
                    .build();
            save(role);
            return;
        }

        // 角色存在但id不一致
        if(!role.getId().equals(userRoleEnums.getId())) {
            role.setId(userRoleEnums.getId());
            updateById(role);
        }
    }
}
