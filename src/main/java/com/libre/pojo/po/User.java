package com.libre.pojo.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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
    // 姓名
    private String name;
    // 修改时间
    private LocalDateTime updateTime;
}
