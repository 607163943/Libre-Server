package com.libre.controller.user;

import com.libre.pojo.dto.user.SearchDTO;
import com.libre.pojo.vo.user.SearchBookVO;
import com.libre.result.PageResult;
import com.libre.result.Result;
import com.libre.service.SearchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "用户端搜索接口")
@RequiredArgsConstructor
@RequestMapping("/search")
@RestController("user-search-controller")
class SearchController {
    private final SearchService searchService;

    @ApiOperation("搜索书籍")
    @GetMapping
    public Result<PageResult<List<SearchBookVO>>> search(SearchDTO searchDTO) {
        PageResult<List<SearchBookVO>> pageResult = searchService.search(searchDTO);
        return Result.success(pageResult);
    }
}
