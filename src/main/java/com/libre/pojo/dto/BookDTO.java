package com.libre.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@ApiModel("图书DTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {
    @ApiModelProperty("图书ID")
    private Long id;
    @ApiModelProperty("图书名称")
    private String bookName;
    @ApiModelProperty("作者ID")
    private Long authorId;
    @ApiModelProperty("出版社ID")
    private Long publisherId;
    @ApiModelProperty("封面URL")
    private String coverUrl;
    @ApiModelProperty("ISBN")
    private String isbn;
    @ApiModelProperty("简介")
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
