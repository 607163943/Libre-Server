package com.libre.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel("出版社VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PublisherVO {
    @ApiModelProperty("出版社id")
    private Long id;
    @ApiModelProperty("出版社名称")
    private String publisherName;
}
