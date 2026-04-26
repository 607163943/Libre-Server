package com.libre.pojo.dto.admin;

import com.libre.validation.UpdateGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@ApiModel("出版社DTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PublisherDTO {
    @ApiModelProperty("出版社id")
    @NotNull(message = "出版社id不能为null",groups = {UpdateGroup.class})
    private Long id;
    @ApiModelProperty("出版社名称")
    @NotEmpty(message = "出版社名称不能为空")
    private String publisherName;
}
