package com.libre.pojo.dto.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@ApiModel("申请审核DTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LendReviewApproveDTO {
    @ApiModelProperty("审核ID")
    @NotNull(message = "审核ID不能为空")
    private Long id;
    @ApiModelProperty("审核结果")
    @NotNull(message = "审核结果不能为空")
    private Integer state;
}
