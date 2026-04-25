package com.libre.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@ApiModel("模块分页参数")
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModulePageDTO extends BasePageDTO {
    @ApiModelProperty("模块名称")
    private String moduleName;

    @ApiModelProperty("客户端类型")
    private Integer clientType;
}
