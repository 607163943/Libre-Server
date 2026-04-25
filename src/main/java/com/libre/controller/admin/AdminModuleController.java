package com.libre.controller.admin;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.libre.pojo.dto.ModuleDTO;
import com.libre.pojo.dto.ModulePageDTO;
import com.libre.pojo.po.Module;
import com.libre.pojo.vo.ModulePageVO;
import com.libre.pojo.vo.ModuleVO;
import com.libre.result.PageResult;
import com.libre.result.Result;
import com.libre.service.admin.AdminModuleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "模块管理接口")
@RequestMapping("/admin/module")
@RestController("admin-module-controller")
@RequiredArgsConstructor
public class AdminModuleController {
    private final AdminModuleService adminModuleService;

    @ApiOperation("模块分页查询接口")
    @GetMapping
    public Result<PageResult<List<ModulePageVO>>> pageQueryModule(@Valid ModulePageDTO modulePageDTO) {
        PageResult<List<ModulePageVO>> pageResult = adminModuleService.pageQueryModule(modulePageDTO);
        return Result.success(pageResult);
    }

    @ApiOperation("获取指定模块信息")
    @GetMapping("{moduleId}")
    public Result<ModuleVO> getModule(@PathVariable Long moduleId) {
        Module module = adminModuleService.getById(moduleId);
        ModuleVO moduleVO = BeanUtil.copyProperties(module, ModuleVO.class);
        return Result.success(moduleVO);
    }

    @ApiOperation("获取所有模块")
    @GetMapping("all")
    public Result<List<ModuleVO>> getAllModules() {
        List<Module> moduleList = adminModuleService.getAllModule();
        List<ModuleVO> moduleVOList = BeanUtil.copyToList(moduleList, ModuleVO.class);
        return Result.success(moduleVOList);
    }

    @ApiOperation("模块添加接口")
    @PostMapping
    public Result<Void> addModule(@RequestBody @Valid ModuleDTO moduleDTO) {
        adminModuleService.addModule(moduleDTO);
        return Result.success();
    }

    @ApiOperation("模块修改接口")
    @PutMapping
    public Result<Void> modifyModule(@RequestBody @Valid ModuleDTO moduleDTO) {
        adminModuleService.modifyModule(moduleDTO);
        return Result.success();
    }

    @ApiOperation("模块删除接口")
    @DeleteMapping("{moduleId}")
    public Result<Void> deleteModule(@PathVariable Long moduleId) {
        adminModuleService.deleteModule(moduleId);
        return Result.success();
    }

    @ApiOperation("模块批量删除接口")
    @DeleteMapping
    public Result<Void> deleteBatchModule(@RequestParam List<Long> ids) {
        if (CollUtil.isNotEmpty(ids)) {
            adminModuleService.deleteBatchModule(ids);
        }
        return Result.success();
    }
}
