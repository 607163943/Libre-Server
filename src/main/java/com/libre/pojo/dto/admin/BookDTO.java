package com.libre.pojo.dto.admin;

import com.libre.validation.UpdateGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@ApiModel("图书DTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {
    @ApiModelProperty("图书ID")
    @NotNull(message = "图书ID不能为空",groups = {UpdateGroup.class})
    private Long id;

    @ApiModelProperty("图书名称")
    @NotBlank(message = "图书名称不能为空")
    private String bookName;

    @ApiModelProperty("作者ID")
    @NotNull(message = "作者ID不能为空")
    private Long authorId;

    @ApiModelProperty("出版社ID")
    @NotNull(message = "出版社ID不能为空")
    private Long publisherId;

    @ApiModelProperty("分类ID")
    @NotNull(message = "分类ID不能为空")
    private Long categoryId;

    @ApiModelProperty("封面文件ID")
    private Long fileId;

    @ApiModelProperty("封面URL")
    private String coverUrl;

    @ApiModelProperty("ISBN")
    @NotBlank(message = "ISBN不能为空")
    private String isbn;

    @ApiModelProperty("书架")
    @NotBlank(message = "书架不能为空")
    private String bookshelf;

    @ApiModelProperty("简介")
    private String introduction;

    @ApiModelProperty("出版日期")
    @NotNull(message = "出版日期不能为空")
    private LocalDate publishDate;

    @ApiModelProperty("数量")
    @NotNull(message = "数量不能为空")
    @Min(value = 0,message = "数量不能小于0")
    private Integer number;
}
