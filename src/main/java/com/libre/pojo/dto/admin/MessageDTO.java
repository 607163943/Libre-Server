package com.libre.pojo.dto.admin;

import com.libre.validation.UpdateGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@ApiModel("消息DTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO {
    @ApiModelProperty("消息ID")
    @NotNull(message = "消息ID不能为空", groups = UpdateGroup.class)
    private Long id;

    @ApiModelProperty("标题")
    @NotBlank(message = "标题不能为空")
    private String title;

    @ApiModelProperty("内容")
    @NotBlank(message = "内容不能为空")
    private String content;

    @ApiModelProperty("类型")
    @NotNull(message = "类型不能为空")
    private Integer type;
}
