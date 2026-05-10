package com.libre.service.common.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.libre.constant.LendStatus;
import com.libre.enums.ExceptionEnums;
import com.libre.exception.LendException;
import com.libre.mapper.LendMapper;
import com.libre.pojo.dto.common.OverLend;
import com.libre.pojo.po.Book;
import com.libre.pojo.po.Lend;
import com.libre.service.common.CommonLendService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommonLendServiceImpl extends ServiceImpl<LendMapper, Lend> implements CommonLendService {
    @Value("${business.lend.max-lend-count}")
    private Integer maxRenewCount;
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

    /**
     * 用户借阅图书
     *
     * @param bookId 借阅的图书id
     */
    @Override
    public void userLendBook(Long bookId,Long userId) {
        // 检查用户借阅权限（包括逾期检查、数量限制等）
        checkLendPermission(userId, false);

        // 检查该书籍是否已借阅
        Long lendCount = lambdaQuery()
                .eq(Lend::getBookId, bookId)
                .eq(Lend::getUserId, userId)
                .ne(Lend::getState, LendStatus.RETURN)
                .count();
        if (lendCount > 0) {
            throw new LendException(ExceptionEnums.LEND_USER_LEND_BOOK_EXIST);
        }

        // 检查书籍是否存在
        Book book = checkBookIsNullAndReturnBook(bookId);
        // 检查库存是否为空
        Long lendBookNumber = lambdaQuery()
                .eq(Lend::getBookId, bookId)
                .in(Lend::getState, LendStatus.LEND, LendStatus.OVERDUE)
                .count();
        if (lendBookNumber.equals(book.getNumber())) {
            throw new LendException(ExceptionEnums.LEND_BOOK_EMPTY);
        }

        Lend lend = new Lend();
        lend.setBookId(bookId);
        lend.setUserId(userId);
        // 初始化借阅次数为0
        lend.setRenewCount(0);
        // 初始化为借阅状态
        lend.setState(LendStatus.LEND);
        // 设置借阅时间
        lend.setLendTime(LocalDateTime.now());
        // 默认借阅时间为7天
        lend.setDueTime(LocalDateTime.now().plusDays(7));
        save(lend);
    }

    /**
     * 检查书籍是否存在
     *
     * @param bookId 书籍id
     * @return 书籍
     */
    private Book checkBookIsNullAndReturnBook(Long bookId) {
        // 判断该书籍是否存在
        Book book = Db.lambdaQuery(Book.class)
                .eq(Book::getId, bookId)
                .one();
        if (book == null) {
            throw new LendException(ExceptionEnums.LEND_BOOK_NOT_EXIST);
        }

        return book;
    }

    /**
     * 检查用户借阅权限规则
     *
     * @param userId  用户ID
     * @param isRenew 是否为续借操作
     */
    public void checkLendPermission(Long userId, boolean isRenew) {
        // 1. 检查当前是否有逾期记录
        Long currentOverdueCount = lambdaQuery()
                .eq(Lend::getUserId, userId)
                .eq(Lend::getState, LendStatus.OVERDUE)
                .count();

        if (currentOverdueCount > 0) {
            throw new LendException(ExceptionEnums.LEND_CURRENT_OVERDUE);
        }

        // 2. 计算90天内的逾期次数（已归还但曾经逾期的记录）
        LocalDateTime ninetyDaysAgo = LocalDateTime.now().minusDays(90);
        Long recentOverdueCount = baseMapper.countOverdueLendInPeriod(userId, ninetyDaysAgo);

        // 3. 获取当前用户的借阅数量（未归还的）
        Long currentLendCount = lambdaQuery()
                .eq(Lend::getUserId, userId)
                .in(Lend::getState, LendStatus.LEND, LendStatus.OVERDUE)
                .count();

        // 4. 根据逾期次数确定最大借书数量和是否允许续借
        int maxLendCount;
        boolean canRenew;

        if (recentOverdueCount >= 3) {
            // 90天内逾期3本及以上：最多可借3本书，无法续借
            maxLendCount = 3;
            canRenew = false;
        } else {
            // 90天内逾期低于3本：最多可借10本书，每本书可续借3次
            maxLendCount = 10;
            canRenew = true;
        }

        // 5. 如果是续借操作，检查是否允许续借
        if (isRenew && !canRenew) {
            throw new LendException(ExceptionEnums.LEND_RENEW_NOT_ALLOWED);
        }

        // 6. 如果是借书操作，检查是否达到最大借书数量
        if (!isRenew && currentLendCount >= maxLendCount) {
            throw new LendException(ExceptionEnums.LEND_MAX_COUNT_EXCEED);
        }
    }

    /**
     * 用户续借图书
     *
     * @param bookId 续借的图书id
     */
    @Override
    public void userRenewBook(Long bookId,Long userId) {
        // 检查用户借阅权限（包括逾期检查、是否允许续借等）
        checkLendPermission(userId, true);

        Lend lend = lambdaQuery()
                .eq(Lend::getBookId, bookId)
                .eq(Lend::getUserId, userId)
                .in(Lend::getState, LendStatus.LEND, LendStatus.OVERDUE)
                .one();
        if (lend == null) {
            throw new LendException(ExceptionEnums.LEND_USER_NOT_LEND);
        }

        // 逾期日期更新
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
}
