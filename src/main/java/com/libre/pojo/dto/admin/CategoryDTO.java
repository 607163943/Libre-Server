package com.libre.pojo.dto.admin;

import com.libre.validation.UpdateGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@ApiModel("分类DTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {
    @ApiModelProperty("分类ID")
    @NotNull(message = "分类ID不能为空", groups = UpdateGroup.class)
    private Long id;

    @ApiModelProperty("分类名称")
    @NotBlank(message = "分类名称不能为空")
    private String categoryName;
}
