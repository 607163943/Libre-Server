package com.libre.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.libre.pojo.po.UserRole;
import com.libre.pojo.vo.admin.RoleVO;

import java.util.List;

public interface UserRoleMapper extends BaseMapper<UserRole> {
    /**
     * 获取用户角色信息
     * @param userId 用户id
     * @return 用户角色信息
     */
    List<RoleVO> getUserRoles(Long userId);
}
