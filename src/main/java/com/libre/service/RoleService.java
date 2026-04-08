package com.libre.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.libre.pojo.dto.RoleDTO;
import com.libre.pojo.dto.RolePageDTO;
import com.libre.pojo.po.Role;
import com.libre.pojo.vo.RolePageVO;
import com.libre.result.PageResult;

import java.util.List;

public interface RoleService extends IService<Role> {
    /**
     * 分页查询角色信息
     * @param rolePageDTO 查询参数
     * @return 查询结果
     */
    PageResult<List<RolePageVO>> pageQueryRole(RolePageDTO rolePageDTO);

    /**
     * 添加角色
     * @param roleDTO 角色信息
     */
    void addRole(RoleDTO roleDTO);

    /**
     * 修改角色
     * @param roleDTO 角色信息
     */
    void modifyRole(RoleDTO roleDTO);

    /**
     * 删除角色
     * @param roleId 角色id
     */
    void deleteRole(Long roleId);
}
