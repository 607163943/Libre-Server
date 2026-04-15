package com.libre.pojo.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@ApiModel("搜索图书VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchBookVO {
    @ApiModelProperty("图书id")
    private Long id;
    @ApiModelProperty("图书名称")
    private String bookName;
    @ApiModelProperty("作者名称")
    private String authorName;
    @ApiModelProperty("出版社名称")
    private String publisherName;
    @ApiModelProperty("ISBN")
    private String isbn;
    @ApiModelProperty("封面")
    private String coverUrl;
    @ApiModelProperty("出版时间")
    private LocalDate publishDate;
    @ApiModelProperty("借阅状态 1借阅 2归还 3逾期")
    private Integer status;
    @ApiModelProperty("归还时间")
    private LocalDateTime dueTime;
}
