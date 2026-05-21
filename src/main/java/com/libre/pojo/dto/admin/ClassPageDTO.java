package com.libre.pojo.dto.admin;

import com.libre.pojo.dto.common.BasePageDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@ApiModel("分类分页参数")
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClassPageDTO extends BasePageDTO {
    @ApiModelProperty("分类名称")
    private String className;
}
