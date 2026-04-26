package com.libre.pojo.vo.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@ApiModel("图书VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookVO {
    @ApiModelProperty("图书id")
    private Long id;
    @ApiModelProperty("图书名")
    private String bookName;
    @ApiModelProperty("作者id")
    private Long authorId;
    @ApiModelProperty("出版社id")
    private Long publisherId;
    @ApiModelProperty("封面图片")
    private String coverUrl;
    @ApiModelProperty("国际标准书号")
    private String isbn;
    @ApiModelProperty("图书简介")
    private String introduction;
    @ApiModelProperty("语言")
    private String language;
    @ApiModelProperty("出版日期")
    private LocalDate publishDate;
    @ApiModelProperty("价格")
    private Long price;
    @ApiModelProperty("数量")
    private Integer number;
}
