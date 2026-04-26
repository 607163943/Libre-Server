package com.libre.pojo.dto.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel("分页参数")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BasePageDTO {
    @ApiModelProperty("当前页码")
    private Long page;
    @ApiModelProperty("每页数量")
    private Long pageSize;
}
