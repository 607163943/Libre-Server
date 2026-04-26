package com.libre.pojo.dto.admin;

import com.libre.validation.UpdateGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@ApiModel("模块DTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModuleDTO {
    @ApiModelProperty("模块ID")
    @NotNull(message = "模块ID不能为空", groups = UpdateGroup.class)
    private Long id;

    @ApiModelProperty("模块名称")
    @NotBlank(message = "模块名称不能为空")
    private String moduleName;

    @ApiModelProperty("模块标识")
    @NotBlank(message = "模块标识不能为空")
    private String moduleKey;

    @ApiModelProperty("模块所属客户端类型")
    @NotNull(message = "客户端类型不能为空")
    private Integer clientType;
}
