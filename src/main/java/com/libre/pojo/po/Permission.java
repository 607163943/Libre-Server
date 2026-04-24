package com.libre.pojo.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@TableName("tb_permission")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Permission extends BasePO {
    // 权限码
    private String permissionCode;
    // 权限描述
    private String permissionDesc;
    // 修改时间
    private LocalDateTime updateTime;
}
