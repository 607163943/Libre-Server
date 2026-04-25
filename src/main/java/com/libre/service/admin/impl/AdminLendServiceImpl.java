package com.libre.service.admin.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.libre.mapper.LendMapper;
import com.libre.pojo.po.Lend;
import com.libre.service.admin.AdminLendService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AdminLendServiceImpl extends ServiceImpl<LendMapper, Lend> implements AdminLendService {
}
