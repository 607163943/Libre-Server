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

    /**
     * 用户借阅图书
     *
     * @param bookId 借阅的图书id
     */
    void userLendBook(Long bookId,Long userId);

    /**
     * 用户续借图书
     * @param bookId 续借的图书id
     */
    void userRenewBook(Long bookId,Long userId);
}
