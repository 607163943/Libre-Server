package com.libre.pojo.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@TableName("tb_user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User extends BasePO {
    // 用户名
    private String username;
    // 密码
    private String password;
    // 昵称
    private String nickName;
    // 邮箱
    private String email;
    // 手机号
    private String phone;
    // 上次登录时间
    private LocalDateTime lastLoginTime;
    // 状态(1启用 2借阅逾期冻结 3管理员冻结)
    private Integer state;
    // 修改时间
    private LocalDateTime updateTime;
}
