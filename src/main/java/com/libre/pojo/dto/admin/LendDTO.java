package com.libre.pojo.dto.admin;

import com.libre.validation.UpdateGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@ApiModel("借阅DTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LendDTO {
    @ApiModelProperty("借阅ID")
    @NotNull(message = "借阅ID不能为空", groups = {UpdateGroup.class})
    private Long id;

    @ApiModelProperty("借阅者ID")
    @NotNull(message = "借阅者ID不能为空")
    private Long userId;

    @ApiModelProperty("图书ID")
    @NotNull(message = "图书ID不能为空")
    private Long bookId;

    @ApiModelProperty("借阅状态(1借阅 2归还 3逾期)")
    @NotNull(message = "借阅状态不能为空")
    @Range(min = 1, max = 3, message = "借阅状态范围错误")
    private Integer state;

    @ApiModelProperty("续借次数")
    @NotNull(message = "续借次数不能为空")
    @Range(min = 0, max = 3, message = "续借次数范围错误")
    private Integer renewCount;

    @ApiModelProperty("借阅时间")
    @NotNull(message = "借阅时间不能为空")
    private LocalDateTime lendTime;

    @ApiModelProperty("归还时间")
    private LocalDateTime returnTime;
}
