package com.libre.pojo.vo.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@ApiModel("借阅审核信息")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class LendReviewPageVO {
    @ApiModelProperty("申请ID")
    private Long id;
    @ApiModelProperty("图书ID")
    private Long bookId;
    @ApiModelProperty("图书名称")
    private String bookName;
    @ApiModelProperty("申请类型")
    private Integer applyType;
    @ApiModelProperty("申请借阅天数")
    private Integer lendDays;
    @ApiModelProperty("申请时间")
    private LocalDateTime applyTime;
    @ApiModelProperty("申请状态")
    private Integer state;
}