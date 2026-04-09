package com.libre.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@ApiModel("借阅分页视图对象")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LendPageVO {
    @ApiModelProperty("借阅ID")
    private Long id;
    @ApiModelProperty("借阅者名称")
    private String username;
    @ApiModelProperty("图书名称")
    private String bookName;
    @ApiModelProperty("借阅状态(1借阅 2归还 3逾期)")
    private Integer state;
    @ApiModelProperty("续借次数")
    private Integer renewCount;
    @ApiModelProperty("借阅时间")
    private LocalDateTime lendTime;
    @ApiModelProperty("归还时间")
    private LocalDateTime returnTime;
    @ApiModelProperty("到期时间")
    private LocalDateTime dueTime;
}
