package com.libre.service.app.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.libre.constant.LendReviewApplyType;
import com.libre.constant.LendReviewState;
import com.libre.constant.LendStatus;
import com.libre.enums.ExceptionEnums;
import com.libre.exception.LendReviewException;
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
import com.libre.service.app.AppLendService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class AppLendReviewServiceImpl extends ServiceImpl<LendReviewMapper, LendReview> implements AppLendReviewService {

    private final LendReviewMapper lendReviewMapper;
    private final BookMapper bookMapper;
    private final AppLendService lendService;

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

        // 如果是借阅，查看库存是否充足
        if(submitDTO.getApplyType().equals(LendReviewApplyType.LEND)) {
            //TODO
        }

        // 如果是续借，需要检查是否有正在进行的借阅记录
        if (submitDTO.getApplyType().equals(LendReviewApplyType.RENEW)) {
            // 这里可以添加续借的逻辑检查，比如检查是否有未归还的借阅记录
            // 暂时简化处理
            boolean exists = lendService.lambdaQuery()
                    .eq(Lend::getUserId, userId)
                    .eq(Lend::getBookId, submitDTO.getBookId())
                    .eq(Lend::getState, LendStatus.LEND)
                    .exists();
            if (!exists) {
                throw new LendReviewException(ExceptionEnums.LEND_REVIEW_USER_NOT_LEND);
            }
        }

        // 创建借阅申请记录
        LendReview lendReview = BeanUtil.copyProperties(submitDTO, LendReview.class);
        lendReview.setUserId(userId);
        lendReview.setState(LendReviewState.WAIT); // 设置为待审核状态
        lendReview.setCreateTime(LocalDateTime.now());
        lendReview.setUpdateTime(LocalDateTime.now());

        // 保存申请记录
        save(lendReview);

        // 发布审核事件
        LendReviewEvent lendReviewEvent = new LendReviewEvent(this,
                lendReview.getId(),
                StpUtil.getLoginIdAsLong(),
                lendReview.getBookId(),
                lendReview.getApplyType());
        applicationEventPublisher.publishEvent(lendReviewEvent);

        return BeanUtil.copyProperties(lendReview, AppLendReviewVO.class);
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
