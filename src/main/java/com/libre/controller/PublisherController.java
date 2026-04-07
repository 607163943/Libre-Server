package com.libre.controller;

import cn.hutool.core.bean.BeanUtil;
import com.libre.pojo.dto.PublisherDTO;
import com.libre.pojo.dto.PublisherPageDTO;
import com.libre.pojo.po.Publisher;
import com.libre.pojo.vo.PublisherPageVO;
import com.libre.pojo.vo.PublisherVO;
import com.libre.result.PageResult;
import com.libre.result.Result;
import com.libre.service.PublisherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "出版社管理接口")
@RequestMapping("/publisher")
@RestController
@RequiredArgsConstructor
public class PublisherController {
    private final PublisherService publisherService;

    @ApiOperation("出版社分页查询接口")
    @GetMapping
    public Result<PageResult<List<PublisherPageVO>>> pageQueryPublisher(PublisherPageDTO publisherPageDTO) {
        PageResult<List<PublisherPageVO>> pageResult = publisherService.pageQueryPublisher(publisherPageDTO);
        return Result.success(pageResult);
    }

    @ApiOperation("获取指定出版社信息")
    @GetMapping("{publisherId}")
    public Result<PublisherVO> getPublisher(@PathVariable Long publisherId) {
        Publisher publisher = publisherService.getById(publisherId);
        PublisherVO publisherVO = BeanUtil.copyProperties(publisher, PublisherVO.class);
        return Result.success(publisherVO);
    }

    @ApiOperation("出版社添加接口")
    @PostMapping
    public Result<Void> addPublisher(@RequestBody PublisherDTO publisherDTO) {
        publisherService.addPublisher(publisherDTO);
        return Result.success();
    }

    @ApiOperation("出版社修改接口")
    @PutMapping
    public Result<Void> modifyPublisher(@RequestBody PublisherDTO publisherDTO) {
        publisherService.modifyPublisher(publisherDTO);
        return Result.success();
    }

    @ApiOperation("出版社删除接口")
    @DeleteMapping("{publisherId}")
    private Result<Void> deletePublisher(@PathVariable Long publisherId) {
        publisherService.deletePublisher(publisherId);
        return Result.success();
    }
}
