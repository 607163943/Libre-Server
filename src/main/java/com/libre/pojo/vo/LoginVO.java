package com.libre.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@ApiModel("登录信息")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginVO {
    @ApiModelProperty("用户名")
    private String name;
    @ApiModelProperty("token名称")
    private String tokenName;
    @ApiModelProperty("token值")
    private String tokenValue;
}
