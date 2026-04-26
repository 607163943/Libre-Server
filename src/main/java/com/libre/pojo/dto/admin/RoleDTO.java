package com.libre.pojo.dto.admin;

import com.libre.validation.UpdateGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@ApiModel("角色DTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleDTO {
    @ApiModelProperty("角色ID")
    @NotNull(message = "角色ID不能为空",groups = UpdateGroup.class)
    private Long id;

    @ApiModelProperty("角色名称")
    @NotBlank(message = "角色名称不能为空")
    private String roleName;
}
