package com.libre.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.libre.pojo.dto.admin.AddUserRoleDTO;
import com.libre.pojo.po.UserRole;
import com.libre.pojo.vo.RoleVO;

import java.util.List;

public interface UserRoleService extends IService<UserRole> {
    /**
     * 获取用户角色列表
     * @param userId 用户id
     * @return 用户角色列表
     */
    List<RoleVO> getUserRoles(Long userId);

    /**
     * 分配用户角色
     * @param addUserRoleDTO 添加用户角色信息
     */
    void assignUserRoles(AddUserRoleDTO addUserRoleDTO);
}
