package com.libre.pojo.dto.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OverLend {
    // 用户id
    private Long userId;
    // 图书名
    private String bookName;
    // 逾期日期
    private LocalDateTime dueTime;
}
