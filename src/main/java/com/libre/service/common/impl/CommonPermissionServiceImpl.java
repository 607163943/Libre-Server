package com.libre.service.common.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.libre.mapper.PermissionMapper;
import com.libre.pojo.po.Permission;
import com.libre.service.common.CommonPermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CommonPermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements CommonPermissionService {
    /**
     * 获取权限码
     * @param permissionIds 权限id集合
     * @return 权限码集合
     */
    @Override
    public List<String> getPermissionCodes(List<Long> permissionIds) {
        if(CollUtil.isEmpty(permissionIds)) {
            return Collections.emptyList();
        }
        return baseMapper.getPermissionCodes(permissionIds);
    }
}
