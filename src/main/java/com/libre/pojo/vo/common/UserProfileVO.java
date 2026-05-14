package com.libre.pojo.vo.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel("用户个人信息VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileVO {
    @ApiModelProperty("用户姓名")
    private String name;
    
    @ApiModelProperty("邮箱")
    private String email;
    
    @ApiModelProperty("手机号")
    private String phone;
}