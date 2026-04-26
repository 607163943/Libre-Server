package com.libre.pojo.vo.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel("权限VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminPermissionCodeVO {
    @ApiModelProperty("权限ID")
    private Long id;

    @ApiModelProperty("权限码")
    private String permissionCode;

    @ApiModelProperty("权限描述")
    private String permissionDesc;

    @ApiModelProperty("模块id")
    private Long moduleId;
}
