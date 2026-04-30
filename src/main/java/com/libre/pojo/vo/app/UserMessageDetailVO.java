package com.libre.pojo.vo.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@ApiModel("用户具体消息VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserMessageDetailVO {
    @ApiModelProperty("消息ID")
    private Long id;
    @ApiModelProperty("消息内容")
    private String content;

    @ApiModelProperty("消息名")
    private String title;

    @ApiModelProperty("发送时间")
    private LocalDateTime createTime;

    @ApiModelProperty("消息类型(来自消息表中type字段)")
    private Integer type;
}