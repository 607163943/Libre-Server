package com.libre.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.libre.pojo.dto.AuthorDTO;
import com.libre.pojo.dto.AuthorPageDTO;
import com.libre.pojo.po.Author;
import com.libre.pojo.vo.AuthorPageVO;
import com.libre.pojo.vo.AuthorVO;
import com.libre.result.PageResult;
import com.libre.result.Result;
import com.libre.service.AuthorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation("获取指定作者信息")
    @GetMapping("{authorId}")
    public Result<AuthorVO> getAuthor(@PathVariable Long authorId) {
        Author author = authorService.getById(authorId);
        AuthorVO authorVO = BeanUtil.copyProperties(author, AuthorVO.class);
        return Result.success(authorVO);
    }

    @ApiOperation("获取所有作者")
    @GetMapping("all")
    public Result<List<AuthorVO>> getAllAuthors() {
        List<Author> authorList = authorService.list();
        List<AuthorVO> authorVOList = BeanUtil.copyToList(authorList, AuthorVO.class);
        return Result.success(authorVOList);
    }
    @ApiOperation("作者添加接口")
    @PostMapping
    public Result<Void> addAuthor(@RequestBody AuthorDTO authorDTO) {
        authorService.addAuthor(authorDTO);
        return Result.success();
    }

    @ApiOperation("作者修改接口")
    @PutMapping
    public Result<Void> modifyAuthor(@RequestBody AuthorDTO authorDTO) {
        authorService.modifyAuthor(authorDTO);
        return Result.success();
    }

    @ApiOperation("作者删除接口")
    @DeleteMapping("{authorId}")
    private Result<Void> deleteAuthor(@PathVariable Long authorId) {
        authorService.deleteAuthor(authorId);
        return Result.success();
    }

    @ApiOperation("作者批量删除接口")
    @DeleteMapping
    public Result<Void> deleteAuthors(@RequestParam List<Long> authorIds) {
        if(CollUtil.isNotEmpty(authorIds)) {
            authorService.removeByIds(authorIds);
        }
        return Result.success();
    }
}
