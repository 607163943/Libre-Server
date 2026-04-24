package com.libre.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@ApiModel("角色权限关联分页参数")
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RolePermissionPageDTO extends BasePageDTO {
    @ApiModelProperty("角色ID")
    private Long roleId;

    @ApiModelProperty("权限ID")
    private Long permissionId;
}
