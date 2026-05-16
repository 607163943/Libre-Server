package com.libre.service.app.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.libre.constant.LendReviewApplyType;
import com.libre.constant.LendReviewState;
import com.libre.constant.LendStatus;
import com.libre.enums.ExceptionEnums;
import com.libre.exception.LendException;
import com.libre.exception.LendReviewException;
import com.libre.exception.LibreException;
import com.libre.mapper.BookMapper;
import com.libre.mapper.LendReviewMapper;
import com.libre.pojo.dto.app.LendReviewPageDTO;
import com.libre.pojo.dto.app.LendReviewSubmitDTO;
import com.libre.pojo.event.LendReviewEvent;
import com.libre.pojo.po.Book;
import com.libre.pojo.po.Lend;
import com.libre.pojo.po.LendReview;
import com.libre.pojo.vo.admin.LendReviewVO;
import com.libre.pojo.vo.app.AppLendReviewVO;
import com.libre.pojo.vo.app.LendReviewPageVO;
import com.libre.service.app.AppLendReviewService;
import com.libre.service.common.CommonLendService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class AppLendReviewServiceImpl extends ServiceImpl<LendReviewMapper, LendReview> implements AppLendReviewService {

    private final LendReviewMapper lendReviewMapper;
    private final BookMapper bookMapper;

    private final CommonLendService commonLendService;

    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public IPage<LendReviewPageVO> pageQueryMyLendReview(LendReviewPageDTO pageDTO) {
        // 创建分页对象
        Page<LendReviewPageVO> page = new Page<>(pageDTO.getPage(), pageDTO.getPageSize());

        // 执行分页查询
        return lendReviewMapper.pageQueryMyLendReview(page, pageDTO);
    }

    /**
     * 提交借阅/续借申请
     *
     * @param submitDTO 申请信息
     * @param userId    用户ID
     * @return 申请记录
     */
    @Override
    public AppLendReviewVO submitLendReview(LendReviewSubmitDTO submitDTO, Long userId) {
        // 验证图书是否存在
        Book book = bookMapper.selectById(submitDTO.getBookId());
        if (book == null) {
            throw new LendReviewException(ExceptionEnums.LEND_REVIEW_BOOK_NOT_EXIST);
        }

        // 验证申请类型：1-借阅 2-续借
        if (submitDTO.getApplyType() != 1 && submitDTO.getApplyType() != 2) {
            throw new LendReviewException(ExceptionEnums.LEND_REVIEW_APPLY_TYPE_ERROR);
        }

        // 如果是续借，需要检查是否有正在进行的借阅记录
        if (submitDTO.getApplyType().equals(LendReviewApplyType.RENEW)) {
            // 这里可以添加续借的逻辑检查，比如检查是否有未归还的借阅记录
            // 暂时简化处理
            boolean exists = commonLendService.lambdaQuery()
                    .eq(Lend::getUserId, userId)
                    .eq(Lend::getBookId, submitDTO.getBookId())
                    .eq(Lend::getState, LendStatus.LEND)
                    .exists();
            if (!exists) {
                throw new LendReviewException(ExceptionEnums.LEND_REVIEW_USER_NOT_LEND);
            }
        }

        // 检查是否满足借阅权限
        boolean isRenew = submitDTO.getApplyType().equals(LendReviewApplyType.RENEW);
        commonLendService.checkLendPermission(userId, isRenew);

        // 创建借阅申请记录
        LendReview lendReview = BeanUtil.copyProperties(submitDTO, LendReview.class);
        lendReview.setUserId(userId);
        lendReview.setState(LendReviewState.WAIT); // 设置为待审核状态
        lendReview.setCreateTime(LocalDateTime.now());
        lendReview.setUpdateTime(LocalDateTime.now());

        // 保存申请记录
        save(lendReview);

        // 获取书籍可借数量
        Long usingCount = Db.lambdaQuery(Lend.class)
                .eq(Lend::getBookId, book.getId())
                .in(Lend::getState, LendStatus.LEND, LendStatus.OVERDUE)
                .count();

        Long price = book.getPrice();
        // 数量高于3本且价格低于100元直接通过
        if (price < 10000 && (book.getNumber() - usingCount) > 3) {
            SystemLendReview(book, lendReview);
        } else {
            // 人工审核
            // 没有库存了
            if ((book.getNumber() - usingCount == 0)) {
                throw new LendException(ExceptionEnums.LEND_BOOK_EMPTY);
            }
            // 发布审核事件
            LendReviewEvent lendReviewEvent = new LendReviewEvent(this,
                    lendReview.getId(),
                    StpUtil.getLoginIdAsLong(),
                    lendReview.getBookId(),
                    lendReview.getApplyType());
            applicationEventPublisher.publishEvent(lendReviewEvent);
        }

        return BeanUtil.copyProperties(lendReview, AppLendReviewVO.class);
    }

    /**
     * 系统审核
     */
    private void SystemLendReview(Book book, LendReview lendReview) {
        Integer finalState;
        try {
            finalState = LendReviewState.PASS;
            // 尝试借阅
            Long userId = StpUtil.getLoginIdAsLong();
            if (lendReview.getApplyType().equals(LendReviewApplyType.LEND)) {
                commonLendService.userLendBook(book.getId(), userId);
            } else {
                commonLendService.userRenewBook(book.getId(), userId);
            }
        } catch (LibreException libreException) {
            // 标记为驳回
            finalState = LendReviewState.NOT_PASS;
        }

        Db.lambdaUpdate(LendReview.class)
                .set(LendReview::getState, finalState)
                .eq(LendReview::getId, lendReview.getId())
                .update();
    }

    /**
     * 获取借阅申请记录
     *
     * @param id 申请ID
     * @return 申请记录
     */
    @Override
    public LendReviewVO getReviewRecord(Long id) {
        LendReview lendReview = getById(id);
        return BeanUtil.copyProperties(lendReview, LendReviewVO.class);
    }
}
