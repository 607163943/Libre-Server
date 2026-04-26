package com.libre.pojo.vo.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@ApiModel("用户端图书详情VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDetailVO {
    @ApiModelProperty("书籍id")
    private Long id;
    @ApiModelProperty("书籍名")
    private String bookName;
    @ApiModelProperty("出版社名")
    private String publisherName;
    @ApiModelProperty("作者名")
    private String authorName;
    @ApiModelProperty("书籍简介")
    private String introduction;
    @ApiModelProperty("出版日期")
    private LocalDate publishDate;
    @ApiModelProperty("ISBN")
    private String isbn;
    @ApiModelProperty("语言")
    private String language;
    @ApiModelProperty("书籍封面Url")
    private String coverUrl;
    @ApiModelProperty("借阅状态")
    private Integer state;
}
