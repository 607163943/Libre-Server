package com.libre.pojo.vo.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@ApiModel("作者分页视图对象")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorPageVO {
    @ApiModelProperty("作者id")
    private Long id;
    @ApiModelProperty("作者名称")
    private String authorName;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    private LocalDateTime updateTime;
}
