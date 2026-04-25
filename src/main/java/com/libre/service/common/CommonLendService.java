package com.libre.service.common;

import com.baomidou.mybatisplus.extension.service.IService;
import com.libre.pojo.po.Lend;

public interface CommonLendService extends IService<Lend> {
    /**
     * 查询超时借阅
     */
    void updateOverTimeLend();
}
