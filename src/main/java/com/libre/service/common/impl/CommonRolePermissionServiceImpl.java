package com.libre.service.common.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.libre.mapper.RolePermissionMapper;
import com.libre.pojo.po.RolePermission;
import com.libre.service.common.CommonRolePermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CommonRolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermission> implements CommonRolePermissionService {
}
