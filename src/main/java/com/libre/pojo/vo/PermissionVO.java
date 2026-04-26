package com.libre.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel("权限VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PermissionVO {
    @ApiModelProperty("权限ID")
    private Long id;

    @ApiModelProperty("操作码，权限码格式：客户端码:模块码:操作码")
    private String actionCode;

    @ApiModelProperty("权限描述")
    private String permissionDesc;

    @ApiModelProperty("模块id")
    private Long moduleId;
}
