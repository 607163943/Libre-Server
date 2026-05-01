package com.libre.pojo.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BasePO {
    // 主键
    private Long id;
    // 创建时间
    private LocalDateTime createTime;
    // 删除标记
    private Long isDelete;
}
