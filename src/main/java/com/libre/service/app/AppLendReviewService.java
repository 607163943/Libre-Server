package com.libre.service.app;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.libre.pojo.dto.app.LendReviewPageDTO;
import com.libre.pojo.dto.app.LendReviewSubmitDTO;
import com.libre.pojo.po.LendReview;
import com.libre.pojo.vo.admin.LendReviewVO;
import com.libre.pojo.vo.app.AppLendReviewVO;
import com.libre.pojo.vo.app.LendReviewPageVO;

public interface AppLendReviewService extends IService<LendReview> {
    /**
     * 分页查询用户借阅申请记录
     * @param pageDTO 分页查询参数
     * @return 分页结果
     */
    IPage<LendReviewPageVO> pageQueryMyLendReview(LendReviewPageDTO pageDTO);
    
    /**
     * 提交借阅/续借申请
     * @param submitDTO 申请信息
     * @param userId 用户ID
     */
    AppLendReviewVO submitLendReview(LendReviewSubmitDTO submitDTO, Long userId);

    /**
     * 获取借阅申请记录
     * @param id 申请ID
     * @return 申请记录
     */
    LendReviewVO getReviewRecord(Long id);
}
