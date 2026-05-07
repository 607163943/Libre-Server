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
@TableName("tb_upload_file_ref")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UploadFileRef extends BasePO {
    // 业务id
    private Long serviceId;
    // 业务类型
    private Integer serviceType;
    // 文件id
    private Long fileId;
    // 更新时间
    private LocalDateTime updateTime;
}
