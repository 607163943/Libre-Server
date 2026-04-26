package com.libre.pojo.vo.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@ApiModel("用户借阅书籍VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyLendBookVO {
    @ApiModelProperty("书籍id")
    private Long id;
    @ApiModelProperty("书籍名")
    private String bookName;
    @ApiModelProperty("封面")
    private String coverUrl;
    @ApiModelProperty("作者")
    private String authorName;
    @ApiModelProperty("出版社")
    private String publisherName;
    @ApiModelProperty("ISBN")
    private String isbn;
    @ApiModelProperty("借阅时间")
    private LocalDateTime lendTime;
    @ApiModelProperty("应归还时间")
    private LocalDateTime dueTime;
}
