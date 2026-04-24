package com.libre.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel("角色权限关联分页视图对象")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RolePermissionPageVO {
    @ApiModelProperty("角色权限ID")
    private Long id;

    @ApiModelProperty("角色名")
    private String roleName;

    @ApiModelProperty("权限码")
    private String permissionCode;

    @ApiModelProperty("权限描述")
    private String permissionDesc;
}
