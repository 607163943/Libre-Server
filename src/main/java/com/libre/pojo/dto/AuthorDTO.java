package com.libre.pojo.dto;

import com.libre.validation.UpdateGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@ApiModel("作者DTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDTO {
    @ApiModelProperty("作者ID")
    @NotNull(message = "作者ID不能为空",groups = UpdateGroup.class)
    private Long id;

    @ApiModelProperty("作者名称")
    @NotBlank(message = "作者名称不能为空")
    private String authorName;
}
