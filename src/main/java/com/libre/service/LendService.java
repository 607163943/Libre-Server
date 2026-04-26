package com.libre.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.libre.pojo.dto.BasePageDTO;
import com.libre.pojo.dto.LendPageDTO;
import com.libre.pojo.dto.user.MyLendPageDTO;
import com.libre.pojo.po.Lend;
import com.libre.pojo.vo.LendPageVO;
import com.libre.pojo.vo.admin.HomeTopBookItem;
import com.libre.pojo.vo.admin.RecentLendTrendItem;
import com.libre.pojo.vo.user.*;
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
     * 借阅图书
      * @param bookId 借阅的图书id
     */
    void userLendBook(Long bookId);

    /**
     * 归还图书
     * @param bookId 归还的图书id
     */
    void userReturnBook(Long bookId);

    /**
     * 获取图书详情
     * @param bookId 图书id
     * @return 图书详情
     */
    BookDetailVO getBookDetail(Long bookId);

    /**
     * 获取用户借阅数据统计
     * @return 用户借阅数据统计
     */
    MyLendDataVO getMyLendData();

    /**
     * 分页查询用户借阅书籍
     * @param myLendPageDTO 查询参数
     * @return 分页结果
     */
    PageResult<List<MyLendBookVO>> pageQueryMyLend(MyLendPageDTO myLendPageDTO);

    /**
     * 用户续借图书
     * @param bookId 续借的图书id
     */
    void userRenewBook(Long bookId);

    /**
     * 获取用户借阅历史数据统计
     * @return 用户借阅历史数据统计
     */
    MyLendHistoryDataVO getMyLendHistoryData();

    /**
     * 分页查询用户历史借阅书籍
     * @param myLendPageDTO 查询参数
     * @return 分页结果
     */
    PageResult<List<MyLendHistoryBookVO>> pageQueryMyLendHistory(MyLendPageDTO myLendPageDTO);

    /**
     * 分页查询用户借阅书籍详情
     * @param basePageDTO 分页参数
     * @return 分页结果
     */
    PageResult<List<MyLendBookDetailVO>> pageQueryMyLendDetail(BasePageDTO basePageDTO);
}
