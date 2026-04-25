package com.libre.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel("模块VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModuleVO {
    @ApiModelProperty("模块ID")
    private Long id;

    @ApiModelProperty("模块名称")
    private String moduleName;

    @ApiModelProperty("模块标识")
    private String moduleKey;

    @ApiModelProperty("模块所属客户端类型")
    private Integer clientType;
}
