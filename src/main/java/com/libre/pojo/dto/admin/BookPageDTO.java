package com.libre.pojo.dto.admin;

import com.libre.pojo.dto.common.BasePageDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@ApiModel("图书分页参数")
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookPageDTO extends BasePageDTO {
    @ApiModelProperty("图书名称")
    private String bookName;
    @ApiModelProperty("作者ID")
    private Long authorId;
    @ApiModelProperty("出版社ID")
    private Long publisherId;
    @ApiModelProperty("国际标准书号")
    private String isbn;
    @ApiModelProperty("语言")
    private String language;
}
