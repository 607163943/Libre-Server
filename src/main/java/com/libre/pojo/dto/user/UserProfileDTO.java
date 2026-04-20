package com.libre.pojo.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@ApiModel("用户端用户个人信息DTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileDTO {
    @ApiModelProperty("用户姓名")
    @NotBlank(message = "用户姓名不能为空")
    private String name;
    @ApiModelProperty("邮箱")
    private String email;
    @ApiModelProperty("手机号")
    private String phone;
}
