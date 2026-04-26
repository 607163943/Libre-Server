package com.libre.pojo.vo.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@ApiModel("用户首页最新图书项")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HomeTopLatestBookItem {
    @ApiModelProperty("图书id")
    private Long id;
    @ApiModelProperty("图书名称")
    private String bookName;
    @ApiModelProperty("作者名称")
    private String authorName;
    @ApiModelProperty("封面")
    private String coverUrl;
    @ApiModelProperty("出版时间")
    private LocalDate publishDate;
    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;
}
