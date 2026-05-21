package com.libre.pojo.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@TableName("tb_user_message")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserMessage extends BasePO {
    // 消息id
    private Long messageId;
    // 用户id
    private Long userId;
    // 是否查看(0未读 1已读)
    private Integer isRead;
    // 查看时间
    private LocalDateTime readTime;
    // 更新时间
    private LocalDateTime updateTime;
}