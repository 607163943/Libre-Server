package com.libre.pojo.vo.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@ApiModel("借阅审核VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LendReviewVO {
    @ApiModelProperty("审核ID")
    private Long id;

    @ApiModelProperty("申请人ID")
    private Long userId;

    @ApiModelProperty("申请的图书ID")
    private Long bookId;

    @ApiModelProperty("申请时间")
    private LocalDateTime applyTime;

    @ApiModelProperty("申请的借阅天数")
    private Integer lendDays;

    @ApiModelProperty("申请类型 1借阅 2续借")
    private Integer applyType;

    @ApiModelProperty("申请状态")
    private Integer state;

    @ApiModelProperty("操作人ID")
    private Long operatorId;
}
