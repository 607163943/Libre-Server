package com.libre.pojo.vo.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel("分类分页视图对象")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClassPageVO {
    @ApiModelProperty("分类id")
    private Long id;
    @ApiModelProperty("分类名称")
    private String className;
}
