package com.libre.pojo.vo.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel("分类VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryVO {
    @ApiModelProperty("分类id")
    private Long id;
    @ApiModelProperty("分类名")
    private String categoryName;
}
