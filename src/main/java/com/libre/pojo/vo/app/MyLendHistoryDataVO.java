package com.libre.pojo.vo.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@ApiModel("用户历史借阅数据统计VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyLendHistoryDataVO {
    @ApiModelProperty("历史借阅总数")
    private Long lendCount;
    @ApiModelProperty("历史借阅逾期归还数")
    private Long overdueLendCount;
}
