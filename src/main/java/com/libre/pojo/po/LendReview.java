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
@TableName("tb_lend_review")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LendReview extends BasePO {
    // 申请人
    private Long userId;
    // 申请的图书
    private Long bookId;
    // 申请时间
    private LocalDateTime applyTime;
    // 申请的借阅天数
    private Integer lendDays;
    // 申请类型 1借阅 2续借
    private Integer applyType;
    // 申请状态
    private Integer state;
    // 操作人
    private Long operatorId;
    // 更新时间
    private LocalDateTime updateTime;
}
