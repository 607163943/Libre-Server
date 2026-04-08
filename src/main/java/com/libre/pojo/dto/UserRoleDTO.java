package com.libre.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel("用户角色DTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRoleDTO {
    @ApiModelProperty("用户角色ID")
    private Long id;
    @ApiModelProperty("用户ID")
    private Long userId;
    @ApiModelProperty("角色ID")
    private Long roleId;
}
