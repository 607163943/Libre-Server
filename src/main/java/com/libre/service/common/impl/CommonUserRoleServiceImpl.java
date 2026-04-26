package com.libre.service.common.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.libre.mapper.UserRoleMapper;
import com.libre.pojo.po.UserRole;
import com.libre.service.common.CommonUserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CommonUserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements CommonUserRoleService {
}
