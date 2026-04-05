package com.libre.controller;

import com.libre.pojo.dto.AuthorPageDTO;
import com.libre.pojo.vo.AuthorPageVO;
import com.libre.result.PageResult;
import com.libre.result.Result;
import com.libre.service.AuthorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "作者管理接口")
@RequestMapping("/author")
@RestController
@RequiredArgsConstructor
public class AuthorController {
    private final AuthorService authorService;

    @ApiOperation("作者分页查询接口")
    @GetMapping
    public Result<PageResult<List<AuthorPageVO>>> pageQueryAuthor(AuthorPageDTO authorPageDTO) {
        PageResult<List<AuthorPageVO>> pageResult = authorService.pageQueryAuthor(authorPageDTO);
        return Result.success(pageResult);
    }
}
