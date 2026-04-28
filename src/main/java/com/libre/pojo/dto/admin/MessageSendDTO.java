package com.libre.pojo.dto.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@ApiModel("消息发送DTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageSendDTO {
    @ApiModelProperty("消息ID")
    @NotNull(message = "消息ID不能为空")
    private Long id;
    @ApiModelProperty("目标群体ID")
    @NotNull(message = "目标群体ID不能为空")
    private Integer target;
}
