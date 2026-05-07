package com.libre.result;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SendMessageResult {
    // 响应码
    private Integer code;
    // 响应信息
    private String msg;
    // 请求ID，用于查询消息发送状态
    private String request_id;
}