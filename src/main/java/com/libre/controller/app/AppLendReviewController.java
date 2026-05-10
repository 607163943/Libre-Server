package com.libre.controller.app;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.libre.pojo.dto.app.LendReviewPageDTO;
import com.libre.pojo.dto.app.LendReviewSubmitDTO;
import com.libre.pojo.vo.admin.LendReviewVO;
import com.libre.pojo.vo.app.AppLendReviewVO;
import com.libre.pojo.vo.app.LendReviewPageVO;
import com.libre.result.PageResult;
import com.libre.result.Result;
import com.libre.service.app.AppLendReviewService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "借阅审核接口")
@RequiredArgsConstructor
@RequestMapping("/app/lend-review")
@RestController
public class AppLendReviewController {
    private final AppLendReviewService lendReviewService;
    
    @ApiOperation("获取我的申请记录")
    @GetMapping("/my")
    public Result<PageResult<List<LendReviewPageVO>>> pageQueryReviewList(LendReviewPageDTO pageDTO) {
        // 设置当前用户ID
        pageDTO.setUserId(StpUtil.getLoginIdAsLong());
        
        // 执行分页查询
        IPage<LendReviewPageVO> page = lendReviewService.pageQueryMyLendReview(pageDTO);
        
        // 封装返回结果
        PageResult<List<LendReviewPageVO>> pageResult = PageResult.<List<LendReviewPageVO>>builder()
                .data(page.getRecords())
                .total(page.getTotal())
                .build();
        
        return Result.success(pageResult);
    }

    @ApiOperation("获取指定申请记录详情")
    @GetMapping("/{id}")
    public Result<LendReviewVO> getLendView(@PathVariable Long id) {
        // 获取申请记录
        LendReviewVO lendReviewVO = lendReviewService.getReviewRecord(id);

        return Result.success(lendReviewVO);
    }
    
    @ApiOperation("提交借阅/续借申请")
    @PostMapping("/submit")
    public Result<AppLendReviewVO> submitLendReview(@RequestBody @Validated LendReviewSubmitDTO submitDTO) {
        // 获取当前用户ID
        Long userId = StpUtil.getLoginIdAsLong();
        
        // 提交申请
        AppLendReviewVO lendReviewVO=lendReviewService.submitLendReview(submitDTO, userId);
        
        return Result.success(lendReviewVO);
    }
}
