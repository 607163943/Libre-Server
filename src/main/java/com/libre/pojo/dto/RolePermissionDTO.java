package com.libre.pojo.dto;

import com.libre.validation.UpdateGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@ApiModel("角色权限关联DTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RolePermissionDTO {
    @ApiModelProperty("记录ID")
    @NotNull(message = "记录ID不能为空", groups = UpdateGroup.class)
    private Long id;

    @ApiModelProperty("角色ID")
    @NotNull(message = "角色ID不能为空")
    private Long roleId;

    @ApiModelProperty("权限ID")
    @NotNull(message = "权限ID不能为空")
    private Long permissionId;
}
