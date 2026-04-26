package com.libre.service.admin.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
        // 删除用户旧角色信息
        adminUserRoleService.lambdaUpdate()
                .set(UserRole::getIsDelete, System.currentTimeMillis())
                .eq(UserRole::getUserId, addUserRoleDTO.getUserId())
                .update();
        // 构建UserRole实体集合
        List<UserRole> userRoleList=new ArrayList<>(addUserRoleDTO.getRoleIds().size());
        for (Long roleId : addUserRoleDTO.getRoleIds()) {
            userRoleList.add(new UserRole(addUserRoleDTO.getUserId(), roleId));
        }

        // 批量添加
        adminUserRoleService.saveBatch(userRoleList);
    }
}
