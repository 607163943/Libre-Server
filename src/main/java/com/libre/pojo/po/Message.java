package com.libre.pojo.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@TableName("tb_message")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message extends BasePO {
    // id
    private Long id;
    // 标题
    private String title;
    // 内容
    private String content;
    // 类型
    private Integer type;
    // 状态(0未发送 1已发送)
    private Integer state;
    // 创建者id
    private Long createUserId;
    // 更新时间
    private LocalDateTime updateTime;
}
