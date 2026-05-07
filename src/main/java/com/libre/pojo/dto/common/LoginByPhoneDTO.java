package com.libre.pojo.dto.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@ApiModel("手机登录信息")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginByPhoneDTO {
    @ApiModelProperty("手机号")
    @NotBlank(message = "手机号不能为空")
    private String phone;
    @ApiModelProperty("验证码")
    @NotBlank(message = "验证码不能为空")
    private String captchaCode;

    private String captchaKey;
}
