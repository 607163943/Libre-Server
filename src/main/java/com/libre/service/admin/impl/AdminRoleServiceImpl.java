package com.libre.service.admin.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.libre.constant.RoleCode;
import com.libre.mapper.RoleMapper;
import com.libre.pojo.po.Role;
import com.libre.service.admin.AdminRoleService;
import com.libre.util.CacheUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Service
public class AdminRoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements AdminRoleService {
    private final CacheUtil cacheUtil;

    /**
     * 获取所有角色列表（带缓存）
     * @return 角色列表
     */
    @Override
    public List<Role> getAllRole() {
        String cacheKey = "admin:role:all";

        // 尝试从缓存中获取
        String cachedData = cacheUtil.get(cacheKey);
        if (cachedData != null) {
            return JSONUtil.toList(cachedData, Role.class);
        }

        // 缓存未命中，查询数据库
        // 排除超管
        List<Role> roleList = lambdaQuery()
                .ne(Role::getId, RoleCode.SUPER_ADMIN)
                .list();

        // 存入缓存，过期时间30分钟
        cacheUtil.set(cacheKey, JSONUtil.toJsonStr(roleList), 30, TimeUnit.MINUTES);

        return roleList;
    }
}
