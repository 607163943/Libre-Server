package com.libre.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.libre.pojo.dto.admin.LendReviewPageDTO;
import com.libre.pojo.po.LendReview;
import com.libre.pojo.vo.admin.LendReviewPageWithRelationVO;
import com.libre.pojo.vo.app.LendReviewPageVO;
import org.apache.ibatis.annotations.Param;

public interface LendReviewMapper extends BaseMapper<LendReview> {
    /**
     * 分页查询用户借阅申请记录
     * @param page 分页条件
     * @param pageDTO 查询参数
     * @return 分页结果
     */
    IPage<LendReviewPageVO> pageQueryMyLendReview(@Param("page") IPage<LendReviewPageVO> page, @Param("pageDTO") com.libre.pojo.dto.app.LendReviewPageDTO pageDTO);
    
    /**
     * 管理端分页查询借阅审核信息（带关联查询）
     * @param page 分页条件
     * @param pageDTO 查询参数
     * @return 分页结果
     */
    IPage<LendReviewPageWithRelationVO> selectAdminPageWithRelations(@Param("page") IPage<LendReview> page, @Param("pageDTO") LendReviewPageDTO pageDTO);
}
