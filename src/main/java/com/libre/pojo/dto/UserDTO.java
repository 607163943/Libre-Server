package com.libre.pojo.dto;

import com.libre.validation.UpdateGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@ApiModel("用户DTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    @ApiModelProperty("用户ID")
    @NotNull(message = "用户ID不能为空",groups = UpdateGroup.class)
    private Long id;

    @ApiModelProperty("用户名")
    @NotBlank(message = "用户名不能为空")
    private String username;

    @ApiModelProperty("密码")
    @NotBlank(message = "密码不能为空")
    private String password;

    @ApiModelProperty("姓名")
    private String name;
}
