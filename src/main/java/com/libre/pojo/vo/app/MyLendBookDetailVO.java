package com.libre.pojo.vo.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@ApiModel("用户借阅书籍详情VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyLendBookDetailVO {
    @ApiModelProperty("图书id")
    private Long id;
    @ApiModelProperty("图书名")
    private String bookName;
    @ApiModelProperty("图书封面")
    private String coverUrl;
    @ApiModelProperty("作者名")
    private String authorName;
    @ApiModelProperty("出版社名")
    private String publisherName;
    @ApiModelProperty("借阅截止日期")
    private LocalDateTime dueTime;
    @ApiModelProperty("借阅状态")
    private Integer state;
}
