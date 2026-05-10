package com.libre.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.libre.pojo.dto.app.LendReviewPageDTO;
import com.libre.pojo.po.LendReview;
import com.libre.pojo.vo.admin.LendReviewPageWithRelationVO;
import com.libre.pojo.vo.app.LendReviewPageVO;
import org.apache.ibatis.annotations.Param;

public interface LendReviewMapper extends BaseMapper<LendReview> {
    IPage<LendReview> selectPageWithCondition(IPage<LendReview> page, QueryWrapper<LendReview> wrapper);
    
    IPage<LendReviewPageWithRelationVO> selectPageWithRelations(@Param("page") IPage<LendReview> page,@Param(Constants.WRAPPER) QueryWrapper<LendReview> wrapper);
    
    /**
     * 分页查询用户借阅申请记录
     * @param page 分页条件
     * @param pageDTO 查询参数
     * @return 分页结果
     */
    IPage<LendReviewPageVO> pageQueryMyLendReview(@Param("page") IPage<LendReviewPageVO> page, @Param("pageDTO") LendReviewPageDTO pageDTO);
}
