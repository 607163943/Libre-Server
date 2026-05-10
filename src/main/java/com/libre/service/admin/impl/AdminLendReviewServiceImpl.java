package com.libre.service.admin.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.libre.constant.LendReviewApplyType;
import com.libre.constant.LendReviewState;
import com.libre.mapper.LendReviewMapper;
import com.libre.pojo.dto.admin.LendReviewApproveDTO;
import com.libre.pojo.dto.admin.LendReviewDTO;
import com.libre.pojo.dto.admin.LendReviewPageDTO;
import com.libre.pojo.po.Book;
import com.libre.pojo.po.LendReview;
import com.libre.pojo.po.User;
import com.libre.pojo.vo.admin.LendReviewPageVO;
import com.libre.pojo.vo.admin.LendReviewPageWithRelationVO;
import com.libre.result.PageResult;
import com.libre.service.admin.AdminBookService;
import com.libre.service.admin.AdminLendReviewService;
import com.libre.service.admin.AdminLendService;
import com.libre.service.admin.AdminUserService;
import com.libre.util.PageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AdminLendReviewServiceImpl extends ServiceImpl<LendReviewMapper, LendReview> implements AdminLendReviewService {
    private final AdminUserService adminUserService;
    private final AdminBookService adminBookService;
    private final AdminLendService lendService;

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
        
        // 构建查询条件
        QueryWrapper<LendReview> wrapper = new QueryWrapper<>();
        wrapper.eq(lendReviewPageDTO.getUserId() != null, "lr.user_id", lendReviewPageDTO.getUserId())
                .eq(lendReviewPageDTO.getBookId() != null, "lr.book_id", lendReviewPageDTO.getBookId())
                .eq(lendReviewPageDTO.getApplyType() != null, "lr.apply_type", lendReviewPageDTO.getApplyType())
                .eq(lendReviewPageDTO.getState() != null, "lr.state", lendReviewPageDTO.getState())
                .ge(lendReviewPageDTO.getStartTime() != null, "lr.apply_time", lendReviewPageDTO.getStartTime())
                .le(lendReviewPageDTO.getEndTime() != null, "lr.apply_time", lendReviewPageDTO.getEndTime());
        
        // 如果需要按申请人姓名或图书名称模糊查询，则需要关联查询
        if (StringUtils.hasText(lendReviewPageDTO.getUserName()) || StringUtils.hasText(lendReviewPageDTO.getBookName())) {
            // 使用自定义SQL进行关联查询
            if (StringUtils.hasText(lendReviewPageDTO.getUserName())) {
                wrapper.like("u.name", lendReviewPageDTO.getUserName());
            }
            if (StringUtils.hasText(lendReviewPageDTO.getBookName())) {
                wrapper.like("b.book_name", lendReviewPageDTO.getBookName());
            }
            
            // 这里需要使用自定义的Mapper方法进行关联查询
            IPage<LendReviewPageWithRelationVO> resultPage = baseMapper.selectPageWithRelations(page, wrapper);
            
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
        } else {
            // 否则使用默认的lambda查询
            page = lambdaQuery()
                    .eq(lendReviewPageDTO.getUserId() != null, LendReview::getUserId, lendReviewPageDTO.getUserId())
                    .eq(lendReviewPageDTO.getBookId() != null, LendReview::getBookId, lendReviewPageDTO.getBookId())
                    .eq(lendReviewPageDTO.getApplyType() != null, LendReview::getApplyType, lendReviewPageDTO.getApplyType())
                    .eq(lendReviewPageDTO.getState() != null, LendReview::getState, lendReviewPageDTO.getState())
                    .ge(lendReviewPageDTO.getStartTime() != null, LendReview::getApplyTime, lendReviewPageDTO.getStartTime())
                    .le(lendReviewPageDTO.getEndTime() != null, LendReview::getApplyTime, lendReviewPageDTO.getEndTime())
                    .page(page);
            
            // 构建VO数据，需要关联查询用户和图书信息
            List<LendReviewPageVO> lendReviewPageVOS = page.getRecords().stream().map(lendReview -> {
                LendReviewPageVO vo = BeanUtil.copyProperties(lendReview, LendReviewPageVO.class);
                
                // 获取用户信息
                if (lendReview.getUserId() != null) {
                    User user = adminUserService.getById(lendReview.getUserId());
                    if (user != null) {
                        vo.setUserName(user.getName());
                    }
                }
                
                // 获取图书信息
                if (lendReview.getBookId() != null) {
                    Book book = adminBookService.getById(lendReview.getBookId());
                    if (book != null) {
                        vo.setBookName(book.getBookName());
                    }
                }
                
                // 获取操作人信息
                if (lendReview.getOperatorId() != null) {
                    User operator = adminUserService.getById(lendReview.getOperatorId());
                    if (operator != null) {
                        vo.setOperatorName(operator.getName());
                    }
                }
                
                return vo;
            }).collect(Collectors.toList());

            return PageResult.<List<LendReviewPageVO>>builder()
                    .total(page.getTotal())
                    .data(lendReviewPageVOS)
                    .build();
        }
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
                lendService.userLendBook(lendReview.getBookId(),lendReview.getUserId());
            }else {
                lendService.userRenewBook(lendReview.getBookId(),lendReview.getUserId());
            }
        }
    }
}
