package com.libre.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.libre.pojo.dto.RolePermissionPageDTO;
import com.libre.pojo.po.RolePermission;
import com.libre.pojo.vo.RolePermissionPageVO;
import org.apache.ibatis.annotations.Param;

public interface RolePermissionMapper extends BaseMapper<RolePermission> {
    /**
     * 分页查询角色权限关联信息
     * @param page 分页参数
     * @param rolePermissionPageDTO 查询参数
     * @return 分页结果
     */
    IPage<RolePermissionPageVO> pageQueryRolePermission(@Param("page") IPage<RolePermissionPageVO> page, @Param("rolePermissionPageDTO") RolePermissionPageDTO rolePermissionPageDTO);
}
