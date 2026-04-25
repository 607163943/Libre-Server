package com.libre.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.libre.pojo.dto.UserRoleDTO;
import com.libre.pojo.dto.UserRolePageDTO;
import com.libre.pojo.dto.admin.AddUserRoleDTO;
import com.libre.pojo.po.UserRole;
import com.libre.pojo.vo.RoleVO;
import com.libre.pojo.vo.UserRolePageVO;
import com.libre.result.PageResult;

import java.util.List;

public interface UserRoleService extends IService<UserRole> {
    /**
     * 分页查询用户角色信息
     * @param userRolePageDTO 查询参数
     * @return 查询结果
     */
    PageResult<List<UserRolePageVO>> pageQueryUserRole(UserRolePageDTO userRolePageDTO);

    /**
     * 添加用户角色
     * @param userRoleDTO 用户角色信息
     */
    void addUserRole(UserRoleDTO userRoleDTO);

    /**
     * 修改用户角色
     * @param userRoleDTO 用户角色信息
     */
    void modifyUserRole(UserRoleDTO userRoleDTO);

    /**
     * 删除用户角色
     * @param userRoleId 用户角色id
     */
    void deleteUserRole(Long userRoleId);

    /**
     * 批量删除用户角色
     * @param ids 用户角色id列表
     */
    void deleteBatchUserRole(List<Long> ids);

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
