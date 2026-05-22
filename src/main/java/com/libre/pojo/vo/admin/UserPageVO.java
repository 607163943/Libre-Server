package com.libre.pojo.vo.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@ApiModel("用户分页视图对象")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPageVO {
    @ApiModelProperty("用户ID")
    private Long id;
    @ApiModelProperty("用户名")
    private String username;
    @ApiModelProperty("昵称")
    private String nickName;
    @ApiModelProperty("手机号")
    private String phone;
    @ApiModelProperty("邮箱")
    private String email;
    @ApiModelProperty("最后登录时间")
    private LocalDateTime lastLoginTime;
    @ApiModelProperty("用户状态 1启用 2逾期借阅冻结 3管理员冻结")
    private Integer state;
}
