package com.libre.pojo.dto.app;

import com.libre.pojo.dto.common.BasePageDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@ApiModel("用户消息分页参数")
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserMessagePageDTO extends BasePageDTO {
    @ApiModelProperty("用户ID,登录后自动获取")
    private Long userId;

    @ApiModelProperty("消息类型(0表示查询所有、1查询未读消息，2查询已读消息)")
    private Integer type;
}