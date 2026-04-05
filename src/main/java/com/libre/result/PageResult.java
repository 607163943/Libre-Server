package com.libre.result;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@ApiModel("分页结果")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResult<T> {
    private T data;
    private Long total;
}
