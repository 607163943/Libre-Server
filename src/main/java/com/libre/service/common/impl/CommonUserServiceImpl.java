package com.libre.service.common.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.libre.mapper.UserMapper;
import com.libre.pojo.po.User;
import com.libre.service.common.CommonUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CommonUserServiceImpl extends ServiceImpl<UserMapper, User> implements CommonUserService {
}
