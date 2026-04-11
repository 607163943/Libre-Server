package com.libre.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel("最近借阅趋势数据项")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecentLendTrendItem {
    @ApiModelProperty("日期")
    private String date;
    @ApiModelProperty("数量")
    private String count;
}
