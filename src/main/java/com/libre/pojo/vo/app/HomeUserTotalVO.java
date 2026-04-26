package com.libre.pojo.vo.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@ApiModel("用户首页统计数据")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HomeUserTotalVO {
    @ApiModelProperty("正在借阅")
    private Long lendCount;
    @ApiModelProperty("即将逾期")
    private Long soonOverdueCount;
    @ApiModelProperty("逾期借阅")
    private Long overdueCount;
}
