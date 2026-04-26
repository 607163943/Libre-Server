package com.libre.pojo.vo.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel("作者VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorVO {
    @ApiModelProperty("作者id")
    private Long id;
    @ApiModelProperty("作者名")
    private String authorName;
}
