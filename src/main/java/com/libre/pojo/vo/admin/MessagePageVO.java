package com.libre.pojo.vo.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel("消息分页视图对象")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessagePageVO {
    @ApiModelProperty("消息ID")
    private Long id;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("类型")
    private Integer type;

    @ApiModelProperty("状态 0未发送 1已发送")
    private Integer state;

    @ApiModelProperty("创建者ID")
    private Long createUserId;
}
