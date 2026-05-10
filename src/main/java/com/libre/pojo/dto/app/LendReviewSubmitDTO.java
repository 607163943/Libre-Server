package com.libre.pojo.dto.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Min;

@ApiModel("提交借阅/续借申请DTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LendReviewSubmitDTO {
    
    @ApiModelProperty(value = "图书ID", required = true)
    @NotNull(message = "图书ID不能为空")
    private Long bookId;

    @ApiModelProperty(value = "申请状态：0-待提交 1-待审核 2-通过 3-驳回", required = true)
    @NotNull(message = "申请状态不能为空")
    private Integer state;
    
    @ApiModelProperty(value = "申请类型：1-借阅 2-续借", required = true)
    @NotNull(message = "申请类型不能为空")
    private Integer applyType;
    
    @ApiModelProperty(value = "借阅天数", required = true)
    @NotNull(message = "借阅天数不能为空")
    @Min(value = 1, message = "借阅天数至少为1天")
    private Integer lendDays;
}