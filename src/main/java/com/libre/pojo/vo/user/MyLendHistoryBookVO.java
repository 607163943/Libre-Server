package com.libre.pojo.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@ApiModel("用户历史借阅书籍VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyLendHistoryBookVO {
    @ApiModelProperty("书籍id")
    private Long id;
    @ApiModelProperty("书籍名")
    private String bookName;
    @ApiModelProperty("作者名")
    private String authorName;
    @ApiModelProperty("出版社名")
    private String publisherName;
    @ApiModelProperty("ISBN")
    private String isbn;
    @ApiModelProperty("书籍封面")
    private String coverUrl;
    @ApiModelProperty("借阅时间")
    private LocalDateTime lendTime;
    @ApiModelProperty("归还时间")
    private LocalDateTime returnTime;
    @ApiModelProperty("借阅状态")
    private Integer state;
}
