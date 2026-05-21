package com.libre.pojo.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@TableName("tb_class")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Class extends BasePO {
    // 分类名
    private String className;
    // 修改时间
    private LocalDateTime updateTime;
}
