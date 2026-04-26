package com.libre.pojo.vo.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel("用户借阅数据统计VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyLendDataVO {
    @ApiModelProperty("当前借阅数")
    private Integer currentLendCount;
    @ApiModelProperty("逾期借阅数")
    private Integer overdueLendCount;
    @ApiModelProperty("即将逾期借阅数(3天之内)")
    private Integer soonOverdueLendCount;
}
