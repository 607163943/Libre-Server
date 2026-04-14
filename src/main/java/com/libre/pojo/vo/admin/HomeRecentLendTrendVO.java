package com.libre.pojo.vo.admin;

import com.libre.pojo.vo.RecentLendTrendItem;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@ApiModel("最近借阅趋势数据")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HomeRecentLendTrendVO {
    @ApiModelProperty("最近借阅趋势数据")
    private List<RecentLendTrendItem> recentLendTrendItemList;
}
