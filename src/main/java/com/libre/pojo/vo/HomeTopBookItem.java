package com.libre.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel("首页图书排行Top项")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HomeTopBookItem {
    @ApiModelProperty("图书名称")
    private String bookName;
    @ApiModelProperty("借阅次数")
    private Long lendCount;
}
