package com.libre.pojo.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@TableName("tb_book")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book extends BasePO {
    // 图书名
    private String bookName;
    // 作者id
    private Long authorId;
    // 出版社id
    private Long publisherId;
    // 分类id
    private Long categoryId;
    // 书架
    private String bookshelf;
    // 封面图片
    private String coverUrl;
    // 国际标准书号
    private String isbn;
    // 图书简介
    private String introduction;
    // 页数
    private LocalDate publishDate;
    // 数量
    private Long number;
    // 可借数量
    private Long availableNumber;
    // 修改时间
    private LocalDateTime updateTime;
}
