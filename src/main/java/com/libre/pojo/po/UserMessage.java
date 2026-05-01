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
    // id
    private Long id;
    // 用户id
    private Long receiverId;
    // 平台范围(0所有平台 1管理员平台 2读者平台)
    private Integer platformScope;
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