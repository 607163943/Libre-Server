package com.libre.pojo.vo.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@ApiModel("图书分页视图对象")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookPageVO {
    @ApiModelProperty("图书id")
    private Long id;

    @ApiModelProperty("图书名称")
    private String bookName;

    @ApiModelProperty("作者id")
    private Long authorId;

    @ApiModelProperty("作者")
    private String authorName;

    @ApiModelProperty("出版社id")
    private Long publisherId;

    @ApiModelProperty("出版社")
    private String publisherName;

    @ApiModelProperty("分类id")
    private Long categoryId;

    @ApiModelProperty("分类名称")
    private String categoryName;

    @ApiModelProperty("国际标准书号")
    private String isbn;

    @ApiModelProperty("书架")
    private String bookshelf;

    @ApiModelProperty("出版日期")
    private LocalDate publishDate;

    @ApiModelProperty("总库存")
    private Integer number;

    @ApiModelProperty("可借库存")
    private Integer availableNumber;
}
