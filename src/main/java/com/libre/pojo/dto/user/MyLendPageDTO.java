package com.libre.pojo.dto.user;

import com.libre.pojo.dto.BasePageDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@ApiModel("用户借阅书籍搜索DTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyLendPageDTO extends BasePageDTO {
    @ApiModelProperty("搜索关键字(匹配书名、作者、出版社、ISBN)")
    private String keyword;
    @ApiModelProperty("借阅开始时间")
    private LocalDateTime startDate;
    @ApiModelProperty("借阅结束时间")
    private LocalDateTime endDate;
    @ApiModelProperty("用户ID(由系统自动设置)")
    private Long userId;
}
