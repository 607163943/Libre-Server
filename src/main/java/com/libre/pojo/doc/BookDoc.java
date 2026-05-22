package com.libre.pojo.doc;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

import java.time.LocalDate;

@Data
@Document(indexName = "idx_book")
@Setting
public class BookDoc {
    @Id
    private Long id;

    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String bookName;

    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String authorName;

    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String publisherName;

    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String categoryName;

    @Field(type = FieldType.Keyword)
    private String bookshelf;

    @Field(type = FieldType.Keyword)
    private String isbn;

    @Field(type = FieldType.Keyword, index = false)
    private String coverUrl;

    @Field(type = FieldType.Date, format = DateFormat.year_month_day)
    private LocalDate publishDate;
}