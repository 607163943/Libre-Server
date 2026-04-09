package com.libre.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@ApiModel("借阅VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LendVO {
    @ApiModelProperty("借阅ID")
    private Long id;
    @ApiModelProperty("借阅者ID")
    private Long userId;
    @ApiModelProperty("图书ID")
    private Long bookId;
    @ApiModelProperty("借阅状态(1借阅 2归还 3逾期)")
    private Integer state;
    @ApiModelProperty("续借次数")
    private Integer renewCount;
    @ApiModelProperty("应还时间")
    private LocalDateTime returnTime;
    @ApiModelProperty("到期时间")
    private LocalDateTime dueTime;
}
