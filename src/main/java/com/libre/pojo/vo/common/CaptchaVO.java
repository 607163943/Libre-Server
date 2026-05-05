package com.libre.pojo.vo.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@ApiModel("验证码VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CaptchaVO {
    @ApiModelProperty("验证码Base64格式图片")
    private String captchaImgBase64;
    @ApiModelProperty("验证码key")
    private String captchaKey;
}
