package com.libre.pojo.vo.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel("角色VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleVO {
    @ApiModelProperty("角色id")
    private Long id;
    @ApiModelProperty("角色名")
    private String roleName;
}
