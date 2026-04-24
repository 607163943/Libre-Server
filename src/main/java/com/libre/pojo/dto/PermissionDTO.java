package com.libre.pojo.dto;

import com.libre.validation.UpdateGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@ApiModel("权限DTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PermissionDTO {
    @ApiModelProperty("权限ID")
    @NotNull(message = "权限ID不能为空", groups = UpdateGroup.class)
    private Long id;

    @ApiModelProperty("权限码")
    @NotBlank(message = "权限码不能为空")
    private String permissionCode;

    @ApiModelProperty("权限描述")
    @NotBlank(message = "权限描述不能为空")
    private String permissionDesc;
}
