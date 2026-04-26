package com.libre.controller.admin;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.libre.pojo.dto.PublisherDTO;
import com.libre.pojo.dto.PublisherPageDTO;
import com.libre.pojo.po.Publisher;
import com.libre.pojo.vo.PublisherPageVO;
import com.libre.pojo.vo.PublisherVO;
import com.libre.result.PageResult;
import com.libre.result.Result;
import com.libre.service.admin.AdminPublisherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "出版社管理接口")
@RequestMapping("/admin/publisher")
@RestController
@RequiredArgsConstructor
public class AdminPublisherController {
    private final AdminPublisherService adminPublisherService;

    @ApiOperation("出版社分页查询接口")
    @GetMapping
    public Result<PageResult<List<PublisherPageVO>>> pageQueryPublisher(PublisherPageDTO publisherPageDTO) {
        PageResult<List<PublisherPageVO>> pageResult = adminPublisherService.pageQueryPublisher(publisherPageDTO);
        return Result.success(pageResult);
    }

    @ApiOperation("获取指定出版社信息")
    @GetMapping("{publisherId}")
    public Result<PublisherVO> getPublisher(@PathVariable Long publisherId) {
        Publisher publisher = adminPublisherService.getById(publisherId);
        PublisherVO publisherVO = BeanUtil.copyProperties(publisher, PublisherVO.class);
        return Result.success(publisherVO);
    }

    @ApiOperation("获取所有出版社")
    @GetMapping("all")
    public Result<List<PublisherVO>> getAllPublishers() {
        List<Publisher> publisherList = adminPublisherService.getAllPublisher();
        List<PublisherVO> publisherVOList = BeanUtil.copyToList(publisherList, PublisherVO.class);
        return Result.success(publisherVOList);
    }
    @ApiOperation("出版社添加接口")
    @PostMapping
    public Result<Void> addPublisher(@RequestBody @Valid PublisherDTO publisherDTO) {
        adminPublisherService.addPublisher(publisherDTO);
        return Result.success();
    }

    @ApiOperation("出版社修改接口")
    @PutMapping
    public Result<Void> modifyPublisher(@RequestBody @Valid PublisherDTO publisherDTO) {
        adminPublisherService.modifyPublisher(publisherDTO);
        return Result.success();
    }

    @ApiOperation("出版社删除接口")
    @DeleteMapping("{publisherId}")
    private Result<Void> deletePublisher(@PathVariable Long publisherId) {
        adminPublisherService.deletePublisher(publisherId);
        return Result.success();
    }

    @ApiOperation("出版社批量删除接口")
    @DeleteMapping
    public Result<Void> deleteBatchPublisher(@RequestParam List<Long> ids) {
        if(CollUtil.isNotEmpty(ids)) {
            adminPublisherService.deleteBatchPublisher(ids);
        }
        return Result.success();
    }
}
