package com.libre.pojo.dto.app;

import com.libre.pojo.dto.common.BasePageDTO;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@ApiModel("借阅申请记录分页查询DTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LendReviewPageDTO extends BasePageDTO {
    // 用户ID(由系统自动设置)
    private Long userId;
}