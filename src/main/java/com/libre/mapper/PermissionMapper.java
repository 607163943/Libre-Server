package com.libre.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.libre.pojo.dto.admin.PermissionPageDTO;
import com.libre.pojo.po.Permission;
import com.libre.pojo.vo.admin.PermissionPageVO;
import com.libre.pojo.vo.admin.AdminPermissionCodeVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PermissionMapper extends BaseMapper<Permission> {
    /**
     * 分页查询权限信息
     * @param page 分页参数
     * @param permissionPageDTO 查询参数
     * @return 分页结果
     */
    IPage<PermissionPageVO> pageQueryPermission(@Param("page") IPage<PermissionPageVO> page, @Param("permissionPageDTO") PermissionPageDTO permissionPageDTO);

    /**
     * 获取所有权限码
     * @return 权限码列表
     */
    List<AdminPermissionCodeVO> getAllPermissionCodes();

    /**
     * 获取权限码
     * @param permissionIds 权限id集合
     * @return 权限码列表
     */
    List<String> getPermissionCodes(@Param("permissionIds") List<Long> permissionIds);
}
