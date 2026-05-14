package com.libre.service.app.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.libre.constant.LendStatus;
import com.libre.enums.ExceptionEnums;
import com.libre.exception.LendException;
import com.libre.mapper.LendMapper;
import com.libre.pojo.dto.app.MyLendPageDTO;
import com.libre.pojo.dto.common.BasePageDTO;
import com.libre.pojo.po.Lend;
import com.libre.pojo.vo.admin.HomeTopBookItem;
import com.libre.pojo.vo.admin.RecentLendTrendItem;
import com.libre.pojo.vo.app.*;
import com.libre.result.PageResult;
import com.libre.service.app.AppLendService;
import com.libre.util.PageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AppLendServiceImpl extends ServiceImpl<LendMapper, Lend> implements AppLendService {
    /**
     * 获取最近借阅趋势
     *
     * @return 最近借阅趋势
     */
    @Override
    public List<RecentLendTrendItem> getRecentLendTrend() {
        return baseMapper.getRecentLendTrend();
    }

    /**
     * 获取首页图书排行
     *
     * @return 首页图书排行
     */
    @Override
    public List<HomeTopBookItem> getHomeTopBook() {
        return baseMapper.getHomeTopBook();
    }

    @Override
    public List<HomeTopLendBookItem> getHomeTopLendBookList() {
        return baseMapper.getHomeTopLendBookList();
    }


    /**
     * 用户归还图书
     *
     * @param bookId 归还的图书id
     */
    @Override
    public void userReturnBook(Long bookId) {
        // 检查该书籍是否已借阅
        Long lendCount = lambdaQuery()
                .eq(Lend::getBookId, bookId)
                .eq(Lend::getUserId, StpUtil.getLoginIdAsLong())
                .in(Lend::getState, LendStatus.LEND, LendStatus.OVERDUE)
                .count();
        if (lendCount == 0) {
            throw new LendException(ExceptionEnums.LEND_USER_NOT_LEND);
        }

        lambdaUpdate()
                .set(Lend::getState, LendStatus.RETURN)
                .set(Lend::getReturnTime, LocalDateTime.now())
                .eq(Lend::getBookId, bookId)
                .eq(Lend::getUserId, StpUtil.getLoginIdAsLong())
                .update();
    }

    /**
     * 获取用户借阅数据统计
     *
     * @return 用户借阅数据统计
     */
    @Override
    public MyLendDataVO getMyLendData() {
        Long userId = StpUtil.getLoginIdAsLong();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime threeDaysLater = now.plusDays(3);

        // 统计当前借阅数(借阅状态 + 逾期状态)
        Long currentLendCount = lambdaQuery()
                .eq(Lend::getUserId, userId)
                .in(Lend::getState, LendStatus.LEND, LendStatus.OVERDUE)
                .count();

        // 统计逾期借阅数
        Long overdueLendCount = lambdaQuery()
                .eq(Lend::getUserId, userId)
                .eq(Lend::getState, LendStatus.OVERDUE)
                .count();

        // 统计即将逾期借阅数(3天之内，且状态为借阅中)
        Long soonOverdueLendCount = lambdaQuery()
                .eq(Lend::getUserId, userId)
                .eq(Lend::getState, LendStatus.LEND)
                .le(Lend::getDueTime, threeDaysLater)
                .gt(Lend::getDueTime, now)
                .count();

        return new MyLendDataVO(
                currentLendCount.intValue(),
                overdueLendCount.intValue(),
                soonOverdueLendCount.intValue()
        );
    }

    /**
     * 分页查询用户借阅书籍
     *
     * @param myLendPageDTO 查询参数
     * @return 分页结果
     */
    @Override
    public PageResult<List<MyLendBookVO>> pageQueryMyLend(MyLendPageDTO myLendPageDTO) {
        // 设置当前登录用户ID
        myLendPageDTO.setUserId(StpUtil.getLoginIdAsLong());

        // 构建分页条件
        IPage<MyLendBookVO> page = PageUtil.createPage(myLendPageDTO);
        // 查询
        page = baseMapper.pageQueryMyLend(page, myLendPageDTO);

        return PageResult.<List<MyLendBookVO>>builder()
                .total(page.getTotal())
                .data(page.getRecords())
                .build();
    }

    /**
     * 获取用户借阅历史数据统计
     *
     * @return 用户借阅历史数据统计
     */
    @Override
    public MyLendHistoryDataVO getMyLendHistoryData() {
        // 历史借阅
        Long lendCount = lambdaQuery()
                .eq(Lend::getUserId, StpUtil.getLoginIdAsLong())
                .count();

        // 统计归还时间大于逾期时间的书籍数
        Long overdueLendCount = baseMapper.countOverdueLend(StpUtil.getLoginIdAsLong());
        return MyLendHistoryDataVO.builder()
                .lendCount(lendCount)
                .overdueLendCount(overdueLendCount)
                .build();
    }

    /**
     * 分页查询用户历史借阅书籍
     *
     * @param myLendPageDTO 查询参数
     * @return 分页结果
     */
    @Override
    public PageResult<List<MyLendHistoryBookVO>> pageQueryMyLendHistory(MyLendPageDTO myLendPageDTO) {
        // 设置当前登录用户ID
        myLendPageDTO.setUserId(StpUtil.getLoginIdAsLong());

        // 构建分页条件
        IPage<MyLendHistoryBookVO> page = PageUtil.createPage(myLendPageDTO);
        // 查询
        page = baseMapper.pageQueryMyLendHistory(page, myLendPageDTO);

        return PageResult.<List<MyLendHistoryBookVO>>builder()
                .total(page.getTotal())
                .data(page.getRecords())
                .build();
    }

    /**
     * 分页查询用户借阅书籍详情
     *
     * @param basePageDTO 分页参数
     * @return 分页结果
     */
    @Override
    public PageResult<List<MyLendBookDetailVO>> pageQueryMyLendDetail(BasePageDTO basePageDTO) {
        IPage<MyLendBookDetailVO> myLendPage = PageUtil.createPage(basePageDTO);

        Long userId = StpUtil.getLoginIdAsLong();
        // 查询
        myLendPage = baseMapper.pageQueryMyLendDetail(myLendPage, userId);

        return PageResult.<List<MyLendBookDetailVO>>builder()
                .total(myLendPage.getTotal())
                .data(myLendPage.getRecords())
                .build();
    }
}
