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
@TableName("tb_file_upload")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileUpload extends BasePO {
    // 文件md5
    private String fileMd5;
    // 文件名
    private String fileName;
    // 文件路径
    private String ossPath;
    // 文件访问路径
    private String fileUrl;
    // 文件大小
    private Long fileSize;
    // 文件类型
    private String mimeType;
    // 文件上传时间
    private LocalDateTime updateTime;
}
