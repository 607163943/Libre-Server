package com.libre.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@ApiModel("图书排行topVO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HomeTopBookVO {
    @ApiModelProperty("图书排行top项集合")
    private List<HomeTopBookItem> homeTopBookItemList;
}
