package com.libre.service.common;

import com.baomidou.mybatisplus.extension.service.IService;
import com.libre.pojo.dto.common.OverLend;
import com.libre.pojo.po.Lend;

import java.util.List;

public interface CommonLendService extends IService<Lend> {
    /**
     * 查询超时借阅
     */
    void updateOverTimeLend();

    /**
     * 查询超时借阅
     * @return 借阅信息
     */
    List<OverLend> selectOverLend();
}
