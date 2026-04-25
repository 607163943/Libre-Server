package com.libre.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.libre.mapper.UserRoleMapper;
import com.libre.pojo.dto.admin.AddUserRoleDTO;
import com.libre.pojo.po.UserRole;
import com.libre.pojo.vo.RoleVO;
import com.libre.service.UserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

    // 解决自盗用导致的事务失效问题
    @Lazy
    @Resource
    private UserRoleService userRoleService;


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
        userRoleService.lambdaUpdate()
                .set(UserRole::getIsDelete, System.currentTimeMillis())
                .eq(UserRole::getUserId, addUserRoleDTO.getUserId())
                .update();
        // 构建UserRole实体集合
        List<UserRole> userRoleList=new ArrayList<>(addUserRoleDTO.getRoleIds().size());
        for (Long roleId : addUserRoleDTO.getRoleIds()) {
            userRoleList.add(new UserRole(addUserRoleDTO.getUserId(), roleId));
        }

        // 批量添加
        userRoleService.saveBatch(userRoleList);
    }
}
