package com.libre.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel("首页卡片统计数据")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HomeTotalCardVO {
    @ApiModelProperty("图书数量")
    private Long bookCount;
    @ApiModelProperty("读者数量")
    private Long readerCount;
    @ApiModelProperty("今日借阅数量")
    private Long todayLendCount;
}
