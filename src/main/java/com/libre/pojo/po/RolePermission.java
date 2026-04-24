package com.libre.pojo.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@TableName("tb_role_permission")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RolePermission extends BasePO {
    // 角色id
    private Long roleId;
    // 权限id
    private Long permissionId;
}
