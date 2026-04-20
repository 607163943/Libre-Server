package com.libre.pojo.dto;

import com.libre.validation.UpdateGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@ApiModel("用户角色DTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRoleDTO {
    @ApiModelProperty("用户角色ID")
    @NotNull(message = "用户角色ID不能为空",groups = UpdateGroup.class)
    private Long id;

    @ApiModelProperty("用户ID")
    @NotNull(message = "用户ID不能为空")
    private Long userId;

    @ApiModelProperty("角色ID")
    @NotNull(message = "角色ID不能为空")
    private Long roleId;
}
