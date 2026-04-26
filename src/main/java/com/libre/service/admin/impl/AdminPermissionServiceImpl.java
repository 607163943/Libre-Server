package com.libre.service.admin.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.libre.enums.ExceptionEnums;
import com.libre.exception.PermissionException;
import com.libre.mapper.PermissionMapper;
import com.libre.pojo.dto.admin.PermissionDTO;
import com.libre.pojo.dto.admin.PermissionPageDTO;
import com.libre.pojo.po.Permission;
import com.libre.pojo.po.RolePermission;
import com.libre.pojo.vo.admin.PermissionPageVO;
import com.libre.pojo.vo.admin.AdminPermissionCodeVO;
import com.libre.result.PageResult;
import com.libre.service.admin.AdminPermissionService;
import com.libre.service.admin.AdminRolePermissionService;
import com.libre.util.PageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Service
public class AdminPermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements AdminPermissionService {
    private final StringRedisTemplate stringRedisTemplate;
    private final AdminRolePermissionService adminRolePermissionService;

    /**
     * 分页查询权限信息
     *
     * @param permissionPageDTO 查询参数
     * @return 分页结果
     */
    @Override
    public PageResult<List<PermissionPageVO>> pageQueryPermission(PermissionPageDTO permissionPageDTO) {
        // 构建分页条件
        IPage<PermissionPageVO> page = PageUtil.createPage(permissionPageDTO);
        // 查询
        page = baseMapper.pageQueryPermission(page, permissionPageDTO);

        return PageResult.<List<PermissionPageVO>>builder()
                .total(page.getTotal())
                .data(page.getRecords())
                .build();
    }

    /**
     * 添加权限信息
     *
     * @param permissionDTO 权限信息
     */
    @Override
    public void addPermission(PermissionDTO permissionDTO) {
        // 判断是否已存在同名权限码
        Long permissionCount = lambdaQuery()
                .eq(Permission::getActionCode, permissionDTO.getActionCode())
                .eq(Permission::getModuleId,permissionDTO.getModuleId())
                .count();

        if (permissionCount > 0) {
            throw new PermissionException(ExceptionEnums.PERMISSION_EXIST);
        }

        Permission permission = BeanUtil.copyProperties(permissionDTO, Permission.class);

        save(permission);

        // 清除缓存
        stringRedisTemplate.delete("admin:permission:all");
    }

    /**
     * 修改权限信息
     *
     * @param permissionDTO 权限信息
     */
    @Override
    public void modifyPermission(PermissionDTO permissionDTO) {
        // 判断是否已存在不是当前修改权限的同名权限码
        Long count = lambdaQuery()
                .eq(Permission::getActionCode, permissionDTO.getActionCode())
                .eq(Permission::getModuleId, permissionDTO.getModuleId())
                .ne(Permission::getId, permissionDTO.getId())
                .count();
        if (count > 0) {
            throw new PermissionException(ExceptionEnums.PERMISSION_EXIST);
        }

        Permission permission = BeanUtil.copyProperties(permissionDTO, Permission.class);
        updateById(permission);

        // 清除缓存
        stringRedisTemplate.delete("admin:permission:all");
    }

    /**
     * 删除权限信息
     *
     * @param permissionId 权限id
     */
    @Override
    public void deletePermission(Long permissionId) {
        Long rolePermissionCount = adminRolePermissionService.lambdaQuery()
                .eq(RolePermission::getPermissionId, permissionId)
                .count();
        // 这里假设有一个角色权限关联表，需要根据实际业务逻辑实现
        if(rolePermissionCount>0) {
            throw new PermissionException(ExceptionEnums.PERMISSION_HAS_ROLE);
        }

        lambdaUpdate()
                // 使用时间戳标记逻辑删除，避免唯一键冲突
                .set(Permission::getIsDelete, System.currentTimeMillis())
                .eq(Permission::getId, permissionId)
                .update();

        // 清除缓存
        stringRedisTemplate.delete("admin:permission:all");
    }

    /**
     * 批量删除权限信息
     * @param ids 权限id集合
     */
    @Override
    public void deleteBatchPermission(List<Long> ids) {
        Long rolePermissionCount = adminRolePermissionService.lambdaQuery()
                .in(RolePermission::getPermissionId, ids)
                .count();
        // 这里假设有一个角色权限关联表，需要根据实际业务逻辑实现
        if(rolePermissionCount>0) {
            throw new PermissionException(ExceptionEnums.PERMISSION_HAS_ROLE);
        }
        // 这里假设有一个角色权限关联表，需要根据实际业务逻辑实现

        lambdaUpdate()
                .set(Permission::getIsDelete, System.currentTimeMillis())
                .in(Permission::getId, ids)
                .update();

        // 清除缓存
        stringRedisTemplate.delete("admin:permission:all");
    }

    /**
     * 获取所有权限列表（带缓存）
     * @return 权限列表
     */
    @Override
    public List<Permission> getAllPermission() {
        String cacheKey = "admin:permission:all";

        // 尝试从缓存中获取
        String cachedData = stringRedisTemplate.opsForValue().get(cacheKey);
        if (cachedData != null) {
            return JSONUtil.toList(cachedData, Permission.class);
        }

        // 缓存未命中，查询数据库
        List<Permission> permissionList = list();

        // 存入缓存，过期时间30分钟
        stringRedisTemplate.opsForValue().set(cacheKey, JSONUtil.toJsonStr(permissionList), 30, TimeUnit.MINUTES);

        return permissionList;
    }

    /**
     * 获取所有权限码列表（完整权限码 带缓存）
     * @return 权限码列表
     */
    @Override
    public List<AdminPermissionCodeVO> getAllPermissionCodes() {
        String cacheKey = "admin:permission:code-all";

        // 尝试从缓存中获取
        String cachedData = stringRedisTemplate.opsForValue().get(cacheKey);
        if (cachedData != null) {
            return JSONUtil.toList(cachedData, AdminPermissionCodeVO.class);
        }

        // 缓存未命中，查询数据库
        List<AdminPermissionCodeVO> adminPermissionCodeVOS = baseMapper.getAllPermissionCodes();

        // 存入缓存，过期时间30分钟
        stringRedisTemplate.opsForValue().set(cacheKey, JSONUtil.toJsonStr(adminPermissionCodeVOS), 30, TimeUnit.MINUTES);

        return adminPermissionCodeVOS;
    }
}
