package com.libre.pojo.dto.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel("验证码DTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CaptchaDTO {
    @ApiModelProperty("待删除验证码key")
    private String captchaKey;
}
