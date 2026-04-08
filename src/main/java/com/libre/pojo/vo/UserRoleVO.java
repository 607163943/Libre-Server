package com.libre.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel("用户角色VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRoleVO {
    @ApiModelProperty("用户角色ID")
    private Long id;
    @ApiModelProperty("用户ID")
    private Long userId;
    @ApiModelProperty("角色ID")
    private Long roleId;
}
