package com.libre.pojo.dto.admin;

import com.libre.pojo.dto.common.BasePageDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@ApiModel("权限分页参数")
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PermissionPageDTO extends BasePageDTO {
    @ApiModelProperty("权限码")
    private String permissionCode;

    @ApiModelProperty("模块id")
    private Long moduleId;

    @ApiModelProperty("客户端类型 1管理端 2App端")
    private Long clientType;
}
