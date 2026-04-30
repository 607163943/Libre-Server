package com.libre.pojo.dto.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OverMessageInfo {
    // 用户id
    private Long userId;
    // 图书名
    private String bookName;
    // 超时天数
    private Long overDay;
}
