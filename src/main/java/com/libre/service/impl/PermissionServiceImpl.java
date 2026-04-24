package com.libre.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.libre.enums.ExceptionEnums;
import com.libre.exception.PermissionException;
import com.libre.mapper.PermissionMapper;
import com.libre.pojo.dto.PermissionDTO;
import com.libre.pojo.dto.PermissionPageDTO;
import com.libre.pojo.po.Permission;
import com.libre.pojo.vo.PermissionPageVO;
import com.libre.result.PageResult;
import com.libre.service.PermissionService;
import com.libre.util.PageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {
    private final StringRedisTemplate stringRedisTemplate;

    /**
     * 分页查询权限信息
     *
     * @param permissionPageDTO 查询参数
     * @return 分页结果
     */
    @Override
    public PageResult<List<PermissionPageVO>> pageQueryPermission(PermissionPageDTO permissionPageDTO) {
        // 构建分页条件
        IPage<Permission> page = PageUtil.createPage(permissionPageDTO);
        // 查询
        page = lambdaQuery()
                .like(StrUtil.isNotBlank(permissionPageDTO.getPermissionCode())
                        , Permission::getPermissionCode, permissionPageDTO.getPermissionCode())
                .like(StrUtil.isNotBlank(permissionPageDTO.getPermissionDesc())
                        , Permission::getPermissionDesc, permissionPageDTO.getPermissionDesc())
                .page(page);
        // 构建VO数据
        List<PermissionPageVO> permissionPageVOS = BeanUtil.copyToList(page.getRecords(), PermissionPageVO.class);

        return PageResult.<List<PermissionPageVO>>builder()
                .total(page.getTotal())
                .data(permissionPageVOS)
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
                .eq(Permission::getPermissionCode, permissionDTO.getPermissionCode())
                .count();

        if (permissionCount > 0) {
            throw new PermissionException(ExceptionEnums.PERMISSION_EXIST);
        }

        Permission permission = BeanUtil.copyProperties(permissionDTO, Permission.class);
        // 避免前端id残留数据影响
        if (permission.getId() != null) permission.setId(null);
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
                .eq(Permission::getPermissionCode, permissionDTO.getPermissionCode())
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
        // TODO: 需要检查是否存在该权限的角色关联
        // 这里假设有一个角色权限关联表，需要根据实际业务逻辑实现
        
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
        // TODO: 需要检查是否存在这些权限的角色关联
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
}
