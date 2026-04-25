package com.libre.pojo.dto.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@ApiModel("添加用户角色信息")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddUserRoleDTO {
    @ApiModelProperty("用户id")
    @NotNull(message = "用户id不能为空")
    private Long userId;
    @ApiModelProperty("角色id列表")
    @NotNull(message = "角色id列表不能为null")
    private List<Long> roleIds;
}
