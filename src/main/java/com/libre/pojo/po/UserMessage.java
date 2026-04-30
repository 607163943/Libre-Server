package com.libre.pojo.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@TableName("tb_user_message")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserMessage extends BasePO {
    // id
    private Long id;
    // 用户id
    private Long receiverId;
    // 消息id
    private Long messageId;
    // 是否已读(0未读 1已读)
    private Integer isRead;
    // 阅读时间
    private LocalDateTime readTime;
    // 创建时间
    private LocalDateTime createTime;
    // 更新时间
    private LocalDateTime updateTime;
}