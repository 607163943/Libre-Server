package com.libre.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel("出版社DTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PublisherDTO {
    @ApiModelProperty("出版社id")
    private Long id;
    @ApiModelProperty("出版社名称")
    private String publisherName;
}
