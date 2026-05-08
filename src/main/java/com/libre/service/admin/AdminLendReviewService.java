package com.libre.service.admin;

import com.baomidou.mybatisplus.extension.service.IService;
import com.libre.pojo.dto.admin.LendReviewApproveDTO;
import com.libre.pojo.dto.admin.LendReviewDTO;
import com.libre.pojo.dto.admin.LendReviewPageDTO;
import com.libre.pojo.po.LendReview;
import com.libre.pojo.vo.admin.LendReviewPageVO;
import com.libre.result.PageResult;

import java.util.List;

public interface AdminLendReviewService extends IService<LendReview> {
    /**
     * 分页查询借阅审核信息
     * @param lendReviewPageDTO 查询参数
     * @return 查询结果
     */
    PageResult<List<LendReviewPageVO>> pageQueryLendReview(LendReviewPageDTO lendReviewPageDTO);

    /**
     * 添加借阅审核
     * @param lendReviewDTO 借阅审核信息
     */
    void addLendReview(LendReviewDTO lendReviewDTO);

    /**
     * 修改借阅审核
     * @param lendReviewDTO 借阅审核信息
     */
    void modifyLendReview(LendReviewDTO lendReviewDTO);

    /**
     * 删除借阅审核
     * @param lendReviewId 借阅审核id
     */
    void deleteLendReview(Long lendReviewId);

    /**
     * 批量删除借阅审核
     * @param ids 借阅审核id集合
     */
    void deleteBatchLendReview(List<Long> ids);

    /**
     * 审批借阅审核
     * @param lendReviewApproveDTO 审批信息
     */
    void approveLendReview(LendReviewApproveDTO lendReviewApproveDTO);
}
