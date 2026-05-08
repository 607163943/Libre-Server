package com.libre.pojo.dto.admin;

import com.libre.validation.UpdateGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@ApiModel("借阅审核DTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LendReviewDTO {
    @ApiModelProperty("审核ID")
    @NotNull(message = "审核ID不能为空", groups = UpdateGroup.class)
    private Long id;

    @ApiModelProperty("申请人ID")
    @NotNull(message = "申请人ID不能为空")
    private Long userId;

    @ApiModelProperty("申请的图书ID")
    @NotNull(message = "图书ID不能为空")
    private Long bookId;

    @ApiModelProperty("申请时间")
    @NotNull(message = "申请时间不能为空")
    private LocalDateTime applyTime;

    @ApiModelProperty("申请的借阅天数")
    @NotNull(message = "借阅天数不能为空")
    private Integer lendDays;

    @ApiModelProperty("申请类型 1借阅 2续借")
    @NotNull(message = "申请类型不能为空")
    private Integer applyType;

    @ApiModelProperty("申请状态")
    @NotNull(message = "申请状态不能为空")
    private Integer state;

    @ApiModelProperty("操作人ID")
    private Long operatorId;
}
