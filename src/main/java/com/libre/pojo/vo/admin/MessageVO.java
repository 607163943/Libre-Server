package com.libre.pojo.vo.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel("消息VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageVO {
    @ApiModelProperty("消息ID")
    private Long id;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("内容")
    private String content;

    @ApiModelProperty("类型")
    private Integer type;

    @ApiModelProperty("创建者ID")
    private Long createUserId;
}
