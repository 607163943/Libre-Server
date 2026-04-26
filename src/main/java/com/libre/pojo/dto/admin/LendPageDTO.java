package com.libre.pojo.dto.admin;

import com.libre.pojo.dto.common.BasePageDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@ApiModel("借阅分页参数")
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LendPageDTO extends BasePageDTO {
    @ApiModelProperty("借阅者ID")
    private Long userId;
    @ApiModelProperty("图书ID")
    private Long bookId;
    @ApiModelProperty("借阅状态(1借阅 2归还 3逾期)")
    private Integer state;
}
