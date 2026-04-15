package com.libre.pojo.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@ApiModel("首页借阅排行榜")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HomeTopLendBookVO {
    @ApiModelProperty("借阅排行榜")
    private List<HomeTopLendBookItem> homeTopLendBookItemList;
}
