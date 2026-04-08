package com.libre.pojo.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@TableName("tb_role")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role extends BasePO {
    // 角色名
    private String roleName;
    // 修改时间
    private LocalDateTime updateTime;
}
