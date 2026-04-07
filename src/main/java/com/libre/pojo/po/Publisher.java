package com.libre.pojo.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@TableName("tb_publisher")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Publisher extends BasePO {
    // 出版社名称
    private String publisherName;
    // 修改时间
    private LocalDateTime updateTime;
}
