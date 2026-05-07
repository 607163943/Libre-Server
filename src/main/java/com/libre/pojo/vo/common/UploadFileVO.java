package com.libre.pojo.vo.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@ApiModel("上传文件VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UploadFileVO {
    @ApiModelProperty("文件id")
    private Long fileId;
    @ApiModelProperty("文件访问路径")
    private String fileUrl;
}
