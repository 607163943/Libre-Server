package com.libre.pojo.dto.app;

import com.libre.pojo.dto.BasePageDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@ApiModel("用户搜索DTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchDTO extends BasePageDTO {
    @ApiModelProperty("搜索关键字")
    private String keyword;
    @ApiModelProperty("是否按照日期排序")
    private Integer isDateSort;
    @ApiModelProperty("图书状态")
    private Integer state;
}
