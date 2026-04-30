package com.libre.pojo.vo.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@ApiModel("用户消息VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserMessageVO {
    @ApiModelProperty("消息ID")
    private Long id;
    @ApiModelProperty("消息内容")
    private String content;

    @ApiModelProperty("消息名")
    private String title;

    @ApiModelProperty("发送时间(该关联消息的创建时间)")
    private LocalDateTime createTime;

    @ApiModelProperty("是否已读 0未读 1已读")
    private Integer isRead;

    @ApiModelProperty("消息类型 1通告 2提醒 3警告")
    private Integer type;
}