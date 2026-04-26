package com.libre.pojo.vo.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel("出版社分页视图对象")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PublisherPageVO {
    @ApiModelProperty("出版社id")
    private Long id;
    @ApiModelProperty("出版社名称")
    private String publisherName;
}
