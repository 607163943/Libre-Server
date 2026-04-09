package com.libre.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel("借阅DTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LendDTO {
    @ApiModelProperty("借阅ID")
    private Long id;
    @ApiModelProperty("借阅者ID")
    private Long userId;
    @ApiModelProperty("图书ID")
    private Long bookId;
    @ApiModelProperty("借阅状态(1借阅 2归还 3逾期)")
    private Integer state;
}
