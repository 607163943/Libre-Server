package com.libre.pojo.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@ApiModel("用户端修改密码DTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPasswordDTO {
    @ApiModelProperty("旧密码")
    @NotBlank(message = "旧密码不能为空")
    private String oldPassword;
    
    @ApiModelProperty("新密码")
    @NotBlank(message = "新密码不能为空")
    private String newPassword;
}
