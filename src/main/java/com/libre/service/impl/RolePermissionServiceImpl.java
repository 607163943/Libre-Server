package com.libre.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.libre.mapper.RolePermissionMapper;
import com.libre.pojo.dto.RolePermissionDTO;
import com.libre.pojo.dto.RolePermissionPageDTO;
import com.libre.pojo.dto.admin.AddRolePermissionDTO;
import com.libre.pojo.po.RolePermission;
import com.libre.pojo.vo.RolePermissionPageVO;
import com.libre.result.PageResult;
import com.libre.service.RolePermissionService;
import com.libre.util.PageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermission> implements RolePermissionService {

    // 处理自调用事务问题
    @Lazy
    @Resource
    private RolePermissionService rolePermissionService;

    /**
     * 分页查询角色权限关联信息
     *
     * @param rolePermissionPageDTO 查询参数
     * @return 分页结果
     */
    @Override
    public PageResult<List<RolePermissionPageVO>> pageQueryRolePermission(RolePermissionPageDTO rolePermissionPageDTO) {
        // 构建分页条件
        IPage<RolePermissionPageVO> page = PageUtil.createPage(rolePermissionPageDTO);
        // 查询
        page = baseMapper.pageQueryRolePermission(page, rolePermissionPageDTO);
        return PageResult.<List<RolePermissionPageVO>>builder()
                .total(page.getTotal())
                .data(page.getRecords())
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
     *
     * @param ids 记录id集合
     */
    @Override
    public void deleteBatchRolePermission(List<Long> ids) {
        lambdaUpdate()
                .set(RolePermission::getIsDelete, System.currentTimeMillis())
                .in(RolePermission::getId, ids)
                .update();
    }

    @Override
    public List<Long> getRolePermissionIds(Long roleId) {
        List<RolePermission> rolePermissionList = lambdaQuery()
                .eq(RolePermission::getRoleId, roleId)
                .list();

        return rolePermissionList.stream()
                .map(RolePermission::getPermissionId)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void assignRolePermission(AddRolePermissionDTO addRolePermissionDTO) {
        // 删除旧权限
        rolePermissionService.lambdaUpdate()
                .set(RolePermission::getIsDelete, System.currentTimeMillis())
                .eq(RolePermission::getRoleId, addRolePermissionDTO.getRoleId())
                .update();

        // 添加新权限
        // 构建角色权限集合
        List<RolePermission> rolePermissionList = new ArrayList<>(addRolePermissionDTO.getPermissionIds().size());

        for (Long permissionId : addRolePermissionDTO.getPermissionIds()) {
            rolePermissionList.add(new RolePermission(addRolePermissionDTO.getRoleId(), permissionId));
        }

        // 批量插入
        rolePermissionService.saveBatch(rolePermissionList);
    }
}
