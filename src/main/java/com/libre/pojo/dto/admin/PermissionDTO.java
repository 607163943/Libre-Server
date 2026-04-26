package com.libre.pojo.dto.admin;

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

    @ApiModelProperty("操作码，权限码格式：客户端码:模块码:操作码")
    @NotBlank(message = "操作码不能为空")
    private String actionCode;

    @ApiModelProperty("权限描述")
    @NotBlank(message = "权限描述不能为空")
    private String permissionDesc;

    @ApiModelProperty("所属模块")
    @NotNull(message = "模块id不能为空")
    private Long moduleId;
}
