package com.libre.pojo.dto.admin;

import com.libre.pojo.dto.common.BasePageDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@ApiModel("借阅审核分页参数")
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LendReviewPageDTO extends BasePageDTO {
    @ApiModelProperty("申请人ID")
    private Long userId;

    @ApiModelProperty("申请人姓名(模糊查询)")
    private String userName;

    @ApiModelProperty("图书ID")
    private Long bookId;

    @ApiModelProperty("图书名称(模糊查询)")
    private String bookName;

    @ApiModelProperty("申请类型 1借阅 2续借")
    private Integer applyType;

    @ApiModelProperty("申请状态")
    private Integer state;

    @ApiModelProperty("申请开始时间")
    private LocalDateTime startTime;

    @ApiModelProperty("申请结束时间")
    private LocalDateTime endTime;
}
