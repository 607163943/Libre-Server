package com.libre.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.libre.mapper.RolePermissionMapper;
import com.libre.pojo.dto.RolePermissionDTO;
import com.libre.pojo.dto.RolePermissionPageDTO;
import com.libre.pojo.po.RolePermission;
import com.libre.pojo.vo.RolePermissionPageVO;
import com.libre.result.PageResult;
import com.libre.service.RolePermissionService;
import com.libre.util.PageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermission> implements RolePermissionService {

    /**
     * 分页查询角色权限关联信息
     *
     * @param rolePermissionPageDTO 查询参数
     * @return 分页结果
     */
    @Override
    public PageResult<List<RolePermissionPageVO>> pageQueryRolePermission(RolePermissionPageDTO rolePermissionPageDTO) {
        // 构建分页条件
        IPage<RolePermission> page = PageUtil.createPage(rolePermissionPageDTO);
        // 查询
        page = lambdaQuery()
                .eq(rolePermissionPageDTO.getRoleId() != null
                        , RolePermission::getRoleId, rolePermissionPageDTO.getRoleId())
                .eq(rolePermissionPageDTO.getPermissionId() != null
                        , RolePermission::getPermissionId, rolePermissionPageDTO.getPermissionId())
                .page(page);
        // 构建VO数据
        List<RolePermissionPageVO> rolePermissionPageVOS = BeanUtil.copyToList(page.getRecords(), RolePermissionPageVO.class);

        return PageResult.<List<RolePermissionPageVO>>builder()
                .total(page.getTotal())
                .data(rolePermissionPageVOS)
                .build();
    }

    /**
     * 添加角色权限关联信息
     *
     * @param rolePermissionDTO 角色权限关联信息
     */
    @Override
    public void addRolePermission(RolePermissionDTO rolePermissionDTO) {
        // 判断是否已存在相同的角色权限关联
        Long count = lambdaQuery()
                .eq(RolePermission::getRoleId, rolePermissionDTO.getRoleId())
                .eq(RolePermission::getPermissionId, rolePermissionDTO.getPermissionId())
                .count();

        if (count > 0) {
            throw new RuntimeException("该角色已拥有此权限");
        }

        RolePermission rolePermission = BeanUtil.copyProperties(rolePermissionDTO, RolePermission.class);
        // 避免前端id残留数据影响
        if (rolePermission.getId() != null) rolePermission.setId(null);
        save(rolePermission);
    }

    /**
     * 修改角色权限关联信息
     *
     * @param rolePermissionDTO 角色权限关联信息
     */
    @Override
    public void modifyRolePermission(RolePermissionDTO rolePermissionDTO) {
        // 判断是否已存在不是当前修改记录的相同角色权限关联
        Long count = lambdaQuery()
                .eq(RolePermission::getRoleId, rolePermissionDTO.getRoleId())
                .eq(RolePermission::getPermissionId, rolePermissionDTO.getPermissionId())
                .ne(RolePermission::getId, rolePermissionDTO.getId())
                .count();
        if (count > 0) {
            throw new RuntimeException("该角色已拥有此权限");
        }

        RolePermission rolePermission = BeanUtil.copyProperties(rolePermissionDTO, RolePermission.class);
        updateById(rolePermission);
    }

    /**
     * 删除角色权限关联信息
     *
     * @param id 记录id
     */
    @Override
    public void deleteRolePermission(Long id) {
        lambdaUpdate()
                .set(RolePermission::getIsDelete, System.currentTimeMillis())
                .eq(RolePermission::getId, id)
                .update();
    }

    /**
     * 批量删除角色权限关联信息
     * @param ids 记录id集合
     */
    @Override
    public void deleteBatchRolePermission(List<Long> ids) {
        lambdaUpdate()
                .set(RolePermission::getIsDelete, System.currentTimeMillis())
                .in(RolePermission::getId, ids)
                .update();
    }
}
