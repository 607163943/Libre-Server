package com.libre.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.libre.pojo.dto.LendDTO;
import com.libre.pojo.dto.LendPageDTO;
import com.libre.pojo.po.Lend;
import com.libre.pojo.vo.LendPageVO;
import com.libre.pojo.vo.admin.HomeTopBookItem;
import com.libre.pojo.vo.admin.RecentLendTrendItem;
import com.libre.pojo.vo.user.HomeTopLendBookItem;
import com.libre.result.PageResult;

import java.util.List;

public interface LendService extends IService<Lend> {
    /**
     * 分页查询借阅信息
     * @param lendPageDTO 查询参数
     * @return 查询结果
     */
    PageResult<List<LendPageVO>> pageQueryLend(LendPageDTO lendPageDTO);

    /**
     * 添加借阅记录
     * @param lendDTO 借阅信息
     */
    void addLend(LendDTO lendDTO);

    /**
     * 修改借阅记录
     * @param lendDTO 借阅信息
     */
    void modifyLend(LendDTO lendDTO);

    /**
     * 删除借阅记录
     * @param lendId 借阅记录id
     */
    void deleteLend(Long lendId);

    /**
     * 批量删除借阅记录
     * @param ids 借阅记录id列表
     */
    void deleteBatchLend(List<Long> ids);

    /**
     * 获取最近借阅趋势
     * @return 最近借阅趋势
     */
    List<RecentLendTrendItem> getRecentLendTrend();

    /**
     * 获取首页图书排行
     * @return 首页图书排行
     */
    List<HomeTopBookItem> getHomeTopBook();

    /**
     * 获取首页热门借阅图书
     * @return 首页热门借阅图书
     */
    List<HomeTopLendBookItem> getHomeTopLendBookList();

    /**
     * 修改借阅状态
     * @param lendDTO 借阅信息
     */
    void modifyLendStatus(LendDTO lendDTO);

    /**
     * 借阅图书
      * @param bookId 借阅的图书id
     */
    void userLendBook(Long bookId);

    /**
     * 归还图书
     * @param bookId 归还的图书id
     */
    void userReturnBook(Long bookId);
}
