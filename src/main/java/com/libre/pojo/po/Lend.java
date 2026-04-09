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
    // 借阅状态(1借阅 2归还 3逾期)
    private Integer state;
    // 续阅次数
    private Integer renewCount;
    // 应还时间
    private LocalDateTime returnTime;
    // 到期时间
    private LocalDateTime dueTime;
    // 更新时间
    private LocalDateTime updateTime;
}
