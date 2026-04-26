package com.libre.controller.admin;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.libre.pojo.dto.admin.LendDTO;
import com.libre.pojo.dto.admin.LendPageDTO;
import com.libre.pojo.po.Lend;
import com.libre.pojo.vo.admin.LendPageVO;
import com.libre.pojo.vo.admin.LendVO;
import com.libre.result.PageResult;
import com.libre.result.Result;
import com.libre.service.admin.AdminLendService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "借阅管理接口")
@RequestMapping("/admin/lend")
@RestController
@RequiredArgsConstructor
public class AdminLendController {
    private final AdminLendService adminLendService;

    @ApiOperation("借阅分页查询接口")
    @GetMapping
    public Result<PageResult<List<LendPageVO>>> pageQueryLend(LendPageDTO lendPageDTO) {
        PageResult<List<LendPageVO>> pageResult = adminLendService.pageQueryLend(lendPageDTO);
        return Result.success(pageResult);
    }

    @ApiOperation("获取指定借阅记录信息")
    @GetMapping("{lendId}")
    public Result<LendVO> getLend(@PathVariable Long lendId) {
        Lend lend = adminLendService.getById(lendId);
        LendVO lendVO = BeanUtil.copyProperties(lend, LendVO.class);
        return Result.success(lendVO);
    }

    @ApiOperation("获取所有借阅记录")
    @GetMapping("all")
    public Result<List<LendVO>> getAllLends() {
        List<Lend> lendList = adminLendService.list();
        List<LendVO> lendVOList = BeanUtil.copyToList(lendList, LendVO.class);
        return Result.success(lendVOList);
    }

    @ApiOperation("借阅添加接口")
    @PostMapping
    public Result<Void> addLend(@RequestBody @Valid LendDTO lendDTO) {
        adminLendService.addLend(lendDTO);
        return Result.success();
    }

    @ApiOperation("借阅修改接口")
    @PutMapping
    public Result<Void> modifyLend(@RequestBody @Valid LendDTO lendDTO) {
        adminLendService.modifyLend(lendDTO);
        return Result.success();
    }

    @ApiOperation("借阅删除接口")
    @DeleteMapping("{lendId}")
    public Result<Void> deleteLend(@PathVariable Long lendId) {
        adminLendService.deleteLend(lendId);
        return Result.success();
    }

    @ApiOperation("借阅批量删除接口")
    @DeleteMapping
    public Result<Void> deleteBatchLend(@RequestParam List<Long> ids) {
        if(CollUtil.isNotEmpty(ids)) {
            adminLendService.deleteBatchLend(ids);
        }
        return Result.success();
    }
}
