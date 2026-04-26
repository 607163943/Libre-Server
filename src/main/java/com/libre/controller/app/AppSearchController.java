package com.libre.controller.app;

import com.libre.pojo.dto.app.SearchDTO;
import com.libre.pojo.vo.app.SearchBookVO;
import com.libre.result.PageResult;
import com.libre.result.Result;
import com.libre.service.app.AppSearchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "用户端搜索接口")
@RequiredArgsConstructor
@RequestMapping("/user/search")
@RestController
class AppSearchController {
    private final AppSearchService appSearchService;

    @ApiOperation("搜索书籍")
    @GetMapping
    public Result<PageResult<List<SearchBookVO>>> search(SearchDTO searchDTO) {
        PageResult<List<SearchBookVO>> pageResult = appSearchService.search(searchDTO);
        return Result.success(pageResult);
    }
}
