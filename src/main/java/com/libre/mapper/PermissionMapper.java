package com.libre.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.libre.pojo.dto.PermissionPageDTO;
import com.libre.pojo.po.Permission;
import com.libre.pojo.vo.PermissionPageVO;
import org.apache.ibatis.annotations.Param;

public interface PermissionMapper extends BaseMapper<Permission> {
    /**
     * 分页查询权限信息
     * @param page 分页参数
     * @param permissionPageDTO 查询参数
     * @return 分页结果
     */
    IPage<PermissionPageVO> pageQueryPermission(@Param("page") IPage<PermissionPageVO> page, @Param("permissionPageDTO") PermissionPageDTO permissionPageDTO);
}
