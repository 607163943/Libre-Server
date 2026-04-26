package com.libre.service.common.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.libre.mapper.RoleMapper;
import com.libre.pojo.po.Role;
import com.libre.service.common.CommonRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CommonRoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements CommonRoleService {
}
