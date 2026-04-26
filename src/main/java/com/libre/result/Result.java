package com.libre.result;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel("统一返回结果")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {
    private Integer code;
    private T data;
    private String msg;

    public static <T> Result<T> of(Integer code,T data,String msg) {
        return new Result<>(code,data,msg);
    }

    public static <T> Result<T> success(T data) {
        return Result.of(200,data,"success");
    }

    public static <T> Result<T> success() {
        return success(null);
    }

    public static <T> Result<T> error(Integer code,String msg) {
        return Result.of(code,null,msg);
    }
}
