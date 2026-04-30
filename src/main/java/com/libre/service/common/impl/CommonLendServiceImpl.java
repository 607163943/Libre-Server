package com.libre.service.common.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.libre.mapper.LendMapper;
import com.libre.pojo.dto.common.OverLend;
import com.libre.pojo.po.Lend;
import com.libre.service.common.CommonLendService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommonLendServiceImpl extends ServiceImpl<LendMapper, Lend> implements CommonLendService {
    /**
     * 查询超时借阅
     */
    @Override
    public void updateOverTimeLend() {
        log.info("开始更新超时借阅-----");
        baseMapper.updateOverTimeLend();
    }

    /**
     * 查询超时借阅
     * @return 借阅信息
     */
    @Override
    public List<OverLend> selectOverLend() {
        log.info("开始查询超时借阅-----");
        return baseMapper.selectOverLend();
    }
}
