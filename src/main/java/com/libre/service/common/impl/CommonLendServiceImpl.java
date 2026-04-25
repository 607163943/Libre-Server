package com.libre.service.common.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.libre.mapper.LendMapper;
import com.libre.pojo.po.Lend;
import com.libre.service.common.CommonLendService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CommonLendServiceImpl extends ServiceImpl<LendMapper, Lend> implements CommonLendService {
    /**
     * 查询超时借阅
     */
    @Override
    public void updateOverTimeLend() {
        baseMapper.updateOverTimeLend();
    }
}
