package com.libre.service.admin.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.libre.constant.*;
import com.libre.mapper.LendReviewMapper;
import com.libre.pojo.dto.admin.LendReviewApproveDTO;
import com.libre.pojo.dto.admin.LendReviewDTO;
import com.libre.pojo.dto.admin.LendReviewPageDTO;
import com.libre.pojo.po.LendReview;
import com.libre.pojo.po.Message;
import com.libre.pojo.po.UserMessage;
import com.libre.pojo.vo.admin.LendReviewPageVO;
import com.libre.pojo.vo.admin.LendReviewPageWithRelationVO;
import com.libre.result.PageResult;
import com.libre.service.admin.*;
import com.libre.service.common.CommonLendService;
import com.libre.util.PageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AdminLendReviewServiceImpl extends ServiceImpl<LendReviewMapper, LendReview> implements AdminLendReviewService {
    private final AdminUserService adminUserService;
    private final AdminBookService adminBookService;

    private final CommonLendService commonLendService;

    private final AdminMessageService messageService;
    private final AdminUserMessageService userMessageService;

    /**
     * 分页查询借阅审核信息
     *
     * @param lendReviewPageDTO 查询参数
     * @return 分页结果
     */
    @Override
    public PageResult<List<LendReviewPageVO>> pageQueryLendReview(LendReviewPageDTO lendReviewPageDTO) {
        // 构建分页条件
        IPage<LendReview> page = PageUtil.createPage(lendReviewPageDTO);
        
        // 使用自定义的Mapper方法进行关联查询，所有查询条件在XML中实现
        IPage<LendReviewPageWithRelationVO> resultPage = baseMapper.selectAdminPageWithRelations(page, lendReviewPageDTO);
        
        // 转换为LendReviewPageVO
        List<LendReviewPageVO> lendReviewPageVOS = resultPage.getRecords().stream().map(vo -> {
            LendReviewPageVO pageVO = new LendReviewPageVO();
            BeanUtil.copyProperties(vo, pageVO);
            return pageVO;
        }).collect(Collectors.toList());

        return PageResult.<List<LendReviewPageVO>>builder()
                .total(resultPage.getTotal())
                .data(lendReviewPageVOS)
                .build();
    }

    /**
     * 添加借阅审核信息
     *
     * @param lendReviewDTO 借阅审核信息
     */
    @Override
    public void addLendReview(LendReviewDTO lendReviewDTO) {
        LendReview lendReview = BeanUtil.copyProperties(lendReviewDTO, LendReview.class);
        // 避免前端id残留数据影响
        if (lendReview.getId() != null) lendReview.setId(null);
        save(lendReview);
    }

    /**
     * 修改借阅审核信息
     *
     * @param lendReviewDTO 借阅审核信息
     */
    @Override
    public void modifyLendReview(LendReviewDTO lendReviewDTO) {
        LendReview lendReview = BeanUtil.copyProperties(lendReviewDTO, LendReview.class);
        // 非待审核均设置操作人id
        if(!lendReview.getState().equals(LendReviewState.WAIT)) {
            lendReview.setOperatorId(StpUtil.getLoginIdAsLong());
        }
        updateById(lendReview);
    }

    /**
     * 删除借阅审核信息
     *
     * @param lendReviewId 借阅审核id
     */
    @Override
    public void deleteLendReview(Long lendReviewId) {
        lambdaUpdate()
                .set(LendReview::getIsDelete,System.currentTimeMillis())
                .eq(LendReview::getId, lendReviewId)
                .update();
    }

    /**
     * 批量删除借阅审核信息
     * @param ids 借阅审核id集合
     */
    @Override
    public void deleteBatchLendReview(List<Long> ids) {
        lambdaUpdate()
                .set(LendReview::getIsDelete,System.currentTimeMillis())
                .in(LendReview::getId, ids)
                .update();
    }

    /**
     * 审批借阅审核信息
     * @param lendReviewApproveDTO 审批信息
     */
    @Transactional
    @Override
    public void approveLendReview(LendReviewApproveDTO lendReviewApproveDTO) {
        LendReview lendReview = lambdaQuery()
                .eq(LendReview::getId, lendReviewApproveDTO.getId())
                .one();
        lendReview.setOperatorId(StpUtil.getLoginIdAsLong());
        lendReview.setState(lendReviewApproveDTO.getState());


        updateById(lendReview);
        // 通过则添加用户借阅数据
        if(lendReviewApproveDTO.getState().equals(LendReviewState.PASS)) {
            if(lendReview.getApplyType().equals(LendReviewApplyType.LEND)) {
                commonLendService.userLendBook(lendReview.getBookId(),lendReview.getUserId());
            }else {
                commonLendService.userRenewBook(lendReview.getBookId(),lendReview.getUserId());
            }

            // 发送消息
            sendLendReviewMessage(lendReview);
        }
    }

    /**
     * 发送消息
     * @param lendReview 借阅审核信息
     */
    private void sendLendReviewMessage(LendReview lendReview) {
        // 给用户发送消息通知
        // 创建消息
        Message message = Message.builder()
                .title("借阅审核结果通知")
                .content("您申请的借阅审核已通过")
                .type(MessageType.LEND)
                .state(MessageState.SEND)
                .createUserId(0L)
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .build();

        messageService.save(message);

        UserMessage userMessage = UserMessage.builder()
                .receiverId(lendReview.getUserId())
                .messageId(message.getId())
                .platformScope(PlatformScope.APP)
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .build();

        userMessageService.save(userMessage);
    }
}
