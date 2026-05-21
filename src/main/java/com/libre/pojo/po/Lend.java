package com.libre.pojo.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@TableName("tb_lend")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Lend extends BasePO {
    // 借阅者id
    private Long userId;
    // 图书id
    private Long bookId;
    // 借阅状态(1借阅 2逾期 3正常归还 4逾期归还)
    private Integer state;
    // 续阅次数(最多1次)
    private Integer renewCount;
    // 借阅时间
    private LocalDateTime lendTime;
    // 归还时间
    private LocalDateTime returnTime;
    // 借阅截止时间
    private LocalDateTime dueTime;
    // 更新时间
    private LocalDateTime updateTime;
}
