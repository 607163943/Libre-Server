package com.libre.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.libre.constant.LendStatus;
import com.libre.enums.ExceptionEnums;
import com.libre.exception.LendException;
import com.libre.mapper.BookMapper;
import com.libre.mapper.LendMapper;
import com.libre.pojo.dto.BasePageDTO;
import com.libre.pojo.dto.LendDTO;
import com.libre.pojo.dto.LendPageDTO;
import com.libre.pojo.dto.user.MyLendPageDTO;
import com.libre.pojo.po.Lend;
import com.libre.pojo.vo.LendPageVO;
import com.libre.pojo.vo.admin.HomeTopBookItem;
import com.libre.pojo.vo.admin.RecentLendTrendItem;
import com.libre.pojo.vo.user.*;
import com.libre.result.PageResult;
import com.libre.service.LendService;
import com.libre.util.PageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class LendServiceImpl extends ServiceImpl<LendMapper, Lend> implements LendService {
    private final BookMapper bookMapper;

    @Value("${business.lend.max-lend-count}")
    private Integer maxRenewCount;

    /**
     * 分页查询借阅信息
     *
     * @param lendPageDTO 查询参数
     * @return 分页结果
     */
    @Override
    public PageResult<List<LendPageVO>> pageQueryLend(LendPageDTO lendPageDTO) {
        // 构建分页条件
        IPage<LendPageVO> page = PageUtil.createPage(lendPageDTO);
        // 查询
        page = baseMapper.pageQueryLend(page, lendPageDTO);

        return PageResult.<List<LendPageVO>>builder()
                .total(page.getTotal())
                .data(page.getRecords())
                .build();
    }

    /**
     * 添加借阅记录
     *
     * @param lendDTO 借阅信息
     */
    @Override
    public void addLend(LendDTO lendDTO) {
        // 判断用户是否已经借阅过该书籍
        Long lendCount = lambdaQuery()
                .eq(Lend::getUserId, lendDTO.getUserId())
                .eq(Lend::getBookId, lendDTO.getBookId())
                .eq(Lend::getState, LendStatus.LEND)
                .count();

        if (lendCount > 0) {
            throw new LendException(ExceptionEnums.LEND_USER_LEND_BOOK_EXIST);
        }
        Lend lend = BeanUtil.copyProperties(lendDTO, Lend.class);
        // 避免前端id残留数据影响
        if (lend.getId() != null) lend.setId(null);
        // 初始化借阅次数为0
        lend.setRenewCount(0);
        // 初始化为借阅状态
        lend.setState(LendStatus.LEND);
        // 默认借阅时间为7天
        lend.setDueTime(LocalDateTime.now().plusDays(7));
        save(lend);
    }

    /**
     * 修改借阅记录
     *
     * @param lendDTO 借阅信息
     */
    @Override
    public void modifyLend(LendDTO lendDTO) {
        // 判断用户是否已经借阅过该书籍
        Long lendCount = lambdaQuery()
                .eq(Lend::getUserId, lendDTO.getUserId())
                .eq(Lend::getBookId, lendDTO.getBookId())
                .eq(Lend::getState, LendStatus.LEND)
                .ne(Lend::getId, lendDTO.getId())
                .count();

        if (lendCount > 0) {
            throw new LendException(ExceptionEnums.LEND_USER_LEND_BOOK_EXIST);
        }

        Lend lend = BeanUtil.copyProperties(lendDTO, Lend.class);
        if(lend.getRenewCount()>maxRenewCount) {
            throw new LendException(ExceptionEnums.LEND_RENEW_OVER_MAX_COUNT);
        }
        updateById(lend);
    }

    /**
     * 删除借阅记录
     *
     * @param lendId 借阅记录id
     */
    @Override
    public void deleteLend(Long lendId) {
        lambdaUpdate()
                // 使用时间戳标记逻辑删除，避免唯一键冲突
                .set(Lend::getIsDelete, System.currentTimeMillis())
                .eq(Lend::getId, lendId)
                .update();
    }

    /**
     * 批量删除借阅记录
     *
     * @param ids 借阅记录id列表
     */
    @Override
    public void deleteBatchLend(List<Long> ids) {
        lambdaUpdate()
                // 使用时间戳标记逻辑删除，避免唯一键冲突
                .set(Lend::getIsDelete, System.currentTimeMillis())
                .in(Lend::getId, ids)
                .update();
    }

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
     * 用户借阅图书
     *
     * @param bookId 借阅的图书id
     */
    @Override
    public void userLendBook(Long bookId) {
        // 检查该书籍是否已借阅
        Long lendCount = lambdaQuery()
                .eq(Lend::getBookId, bookId)
                .eq(Lend::getUserId, StpUtil.getLoginIdAsLong())
                .ne(Lend::getState, LendStatus.RETURN)
                .count();
        if (lendCount > 0) {
            throw new LendException(ExceptionEnums.LEND_USER_LEND_BOOK_EXIST);
        }

        Lend lend = new Lend();
        lend.setBookId(bookId);
        lend.setUserId(StpUtil.getLoginIdAsLong());
        // 初始化借阅次数为0
        lend.setRenewCount(0);
        // 初始化为借阅状态
        lend.setState(LendStatus.LEND);
        // 默认借阅时间为7天
        lend.setDueTime(LocalDateTime.now().plusDays(7));
        save(lend);
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
     * 获取图书详情
     *
     * @param bookId 图书id
     * @return 图书详情
     */
    @Override
    public BookDetailVO getBookDetail(Long bookId) {
        BookDetailVO bookDetail = bookMapper.getBookDetail(bookId);
        if (!StpUtil.isLogin()) {
            return bookDetail;
        }

        Lend lend = lambdaQuery()
                .eq(Lend::getBookId, bookId)
                .eq(Lend::getUserId, StpUtil.getLoginIdAsLong())
                .in(Lend::getState, LendStatus.LEND, LendStatus.OVERDUE)
                .one();
        if (lend == null) {
            return bookDetail;
        }

        bookDetail.setState(lend.getState());

        return bookDetail;
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
     * 用户续借图书
     *
     * @param bookId 续借的图书id
     */
    @Override
    public void userRenewBook(Long bookId) {
        Lend lend = lambdaQuery()
                .eq(Lend::getBookId, bookId)
                .eq(Lend::getUserId, StpUtil.getLoginIdAsLong())
                .in(Lend::getState, LendStatus.LEND, LendStatus.OVERDUE)
                .one();
        if (lend == null) {
            throw new LendException(ExceptionEnums.LEND_USER_NOT_LEND);
        }

        // 逾期则在当前日期续7天
        if (lend.getState().equals(LendStatus.OVERDUE)) {
            lend.setDueTime(LocalDateTime.now().plusDays(7));
        } else {
            // 否则在当前截止日期续7天
            lend.setDueTime(lend.getDueTime().plusDays(7));
        }
        lend.setRenewCount(lend.getRenewCount() + 1);

        // 续借不能超过3次
        if (lend.getRenewCount() > maxRenewCount) {
            throw new LendException(ExceptionEnums.LEND_RENEW_OVER_MAX_COUNT);
        }
        updateById(lend);
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
