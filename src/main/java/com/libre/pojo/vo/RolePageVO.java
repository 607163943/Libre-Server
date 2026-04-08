package com.libre.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel("角色分页视图对象")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RolePageVO {
    @ApiModelProperty("角色id")
    private Long id;
    @ApiModelProperty("角色名称")
    private String roleName;
}
