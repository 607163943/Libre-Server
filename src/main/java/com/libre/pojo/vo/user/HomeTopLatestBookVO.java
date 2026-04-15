package com.libre.pojo.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@ApiModel("用户首页最新图书")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HomeTopLatestBookVO {
    @ApiModelProperty("用户首页最新图书列表")
    private List<HomeTopLatestBookItem> homeTopLatestBookItemList;
}
