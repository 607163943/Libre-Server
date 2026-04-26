package com.libre.pojo.dto.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@ApiModel("添加角色权限关联信息")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddRolePermissionDTO {
    @ApiModelProperty("角色id")
    private Long roleId;
    @ApiModelProperty("权限id列表")
    private List<Long> permissionIds;
}
