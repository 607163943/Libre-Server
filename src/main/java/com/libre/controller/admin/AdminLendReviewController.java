package com.libre.controller.admin;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.libre.pojo.dto.admin.LendReviewApproveDTO;
import com.libre.pojo.dto.admin.LendReviewDTO;
import com.libre.pojo.dto.admin.LendReviewPageDTO;
import com.libre.pojo.po.LendReview;
import com.libre.pojo.vo.admin.LendReviewPageVO;
import com.libre.pojo.vo.admin.LendReviewVO;
import com.libre.result.PageResult;
import com.libre.result.Result;
import com.libre.service.admin.AdminLendReviewService;
import com.libre.validation.UpdateGroup;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.groups.Default;
import java.util.List;

@Api(tags = "借阅审核管理接口")
@RequestMapping("/admin/lend-review")
@RestController
@RequiredArgsConstructor
public class AdminLendReviewController {
    private final AdminLendReviewService adminLendReviewService;

    @ApiOperation("借阅审核分页查询接口")
    @GetMapping
    public Result<PageResult<List<LendReviewPageVO>>> pageQueryLendReview(LendReviewPageDTO lendReviewPageDTO) {
        PageResult<List<LendReviewPageVO>> pageResult = adminLendReviewService.pageQueryLendReview(lendReviewPageDTO);
        return Result.success(pageResult);
    }

    @ApiOperation("获取指定借阅审核信息")
    @GetMapping("{lendReviewId}")
    public Result<LendReviewVO> getLendReview(@PathVariable Long lendReviewId) {
        LendReview lendReview = adminLendReviewService.getById(lendReviewId);
        LendReviewVO lendReviewVO = BeanUtil.copyProperties(lendReview, LendReviewVO.class);
        return Result.success(lendReviewVO);
    }

    @ApiOperation("借阅审核添加接口")
    @PostMapping
    public Result<Void> addLendReview(@RequestBody @Valid LendReviewDTO lendReviewDTO) {
        adminLendReviewService.addLendReview(lendReviewDTO);
        return Result.success();
    }

    @ApiOperation("借阅审核修改接口")
    @PutMapping
    public Result<Void> modifyLendReview(@RequestBody @Validated({Default.class, UpdateGroup.class}) LendReviewDTO lendReviewDTO) {
        adminLendReviewService.modifyLendReview(lendReviewDTO);
        return Result.success();
    }

    @ApiOperation("借阅审核删除接口")
    @DeleteMapping("{lendReviewId}")
    private Result<Void> deleteLendReview(@PathVariable Long lendReviewId) {
        adminLendReviewService.deleteLendReview(lendReviewId);
        return Result.success();
    }

    @ApiOperation("借阅审核批量删除接口")
    @DeleteMapping
    public Result<Void> deleteBatchLendReview(@RequestParam List<Long> ids) {
        if(CollUtil.isNotEmpty(ids)) {
            adminLendReviewService.deleteBatchLendReview(ids);
        }
        return Result.success();
    }

    @ApiOperation("处理借阅审核")
    @PutMapping("/approve")
    public Result<Void> approveLendReview(@RequestBody @Valid LendReviewApproveDTO lendReviewApproveDTO) {
        adminLendReviewService.approveLendReview(lendReviewApproveDTO);
        return Result.success();
    }
}
