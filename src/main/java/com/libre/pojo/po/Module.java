package com.libre.pojo.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@TableName("tb_module")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Module extends BasePO {
    // 模块名称
    private String moduleName;
    // 模块标识
    private String moduleKey;
    // 模块所属客户端类型
    private Integer clientType;
}
