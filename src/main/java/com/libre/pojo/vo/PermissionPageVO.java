package com.libre.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel("权限分页视图对象")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PermissionPageVO {
    @ApiModelProperty("权限ID")
    private Long id;

    @ApiModelProperty("权限码")
    private String permissionCode;

    @ApiModelProperty("权限描述")
    private String permissionDesc;

    @ApiModelProperty("模块名")
    private String moduleName;

    @ApiModelProperty("客户端类型 1管理端 2App端")
    private Integer clientType;
}
