package com.libre.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.libre.pojo.po.LendReview;
import com.libre.pojo.vo.admin.LendReviewPageWithRelationVO;

public interface LendReviewMapper extends BaseMapper<LendReview> {
    IPage<LendReview> selectPageWithCondition(IPage<LendReview> page, QueryWrapper<LendReview> wrapper);
    
    IPage<LendReviewPageWithRelationVO> selectPageWithRelations(IPage<LendReview> page, QueryWrapper<LendReview> wrapper);
}
