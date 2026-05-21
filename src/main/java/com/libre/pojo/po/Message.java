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
@TableName("tb_message")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message extends BasePO {
    // 标题
    private String title;
    // 内容
    private String content;
    // 消息类型(1系统公告 2系统通知 3逾期警告 4借阅成功通知)
    private Integer type;
    // 负责端(1用户端 2管理端 3全站)
    private Integer clientType;
    // 是否发送(0未发送 1已发送)
    private Integer isSend;
    // 发送时间
    private LocalDateTime sendTime;
    // 创建者id
    private Long createUserId;
    // 更新时间
    private LocalDateTime updateTime;
}
