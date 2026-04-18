package com.libre.pojo.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel("用户端修改密码DTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPasswordDTO {
    @ApiModelProperty("旧密码")
    private String oldPassword;
    
    @ApiModelProperty("新密码")
    private String newPassword;
}
