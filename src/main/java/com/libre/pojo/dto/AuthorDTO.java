package com.libre.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel("作者DTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDTO {
    @ApiModelProperty("作者ID")
    private Long id;
    @ApiModelProperty("作者名称")
    private String authorName;
}
