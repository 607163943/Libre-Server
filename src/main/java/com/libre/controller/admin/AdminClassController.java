package com.libre.controller.admin;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.libre.pojo.dto.admin.ClassDTO;
import com.libre.pojo.dto.admin.ClassPageDTO;
import com.libre.pojo.po.Class;
import com.libre.pojo.vo.admin.ClassPageVO;
import com.libre.pojo.vo.admin.ClassVO;
import com.libre.result.PageResult;
import com.libre.result.Result;
import com.libre.service.admin.AdminClassService;
import com.libre.validation.UpdateGroup;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.groups.Default;
import java.util.List;

@Api(tags = "分类管理接口")
@RequestMapping("/admin/class")
@RestController
@RequiredArgsConstructor
public class AdminClassController {
    private final AdminClassService adminClassService;

    @ApiOperation("分类分页查询接口")
    @GetMapping
    public Result<PageResult<List<ClassPageVO>>> pageQueryClass(@Valid ClassPageDTO classPageDTO) {
        PageResult<List<ClassPageVO>> pageResult = adminClassService.pageQueryClass(classPageDTO);
        return Result.success(pageResult);
    }

    @ApiOperation("获取指定分类信息")
    @GetMapping("{classId}")
    public Result<ClassVO> getClass(@PathVariable Long classId) {
        Class clazz = adminClassService.getById(classId);
        ClassVO classVO = BeanUtil.copyProperties(clazz, ClassVO.class);
        return Result.success(classVO);
    }

    @ApiOperation("获取所有分类")
    @GetMapping("all")
    public Result<List<ClassVO>> getAllClasses() {
        List<Class> classList = adminClassService.getAllClass();
        List<ClassVO> classVOList = BeanUtil.copyToList(classList, ClassVO.class);
        return Result.success(classVOList);
    }

    @ApiOperation("分类添加接口")
    @PostMapping
    public Result<Void> addClass(@RequestBody @Valid ClassDTO classDTO) {
        adminClassService.addClass(classDTO);
        return Result.success();
    }

    @ApiOperation("分类修改接口")
    @PutMapping
    public Result<Void> modifyClass(@RequestBody @Validated({Default.class, UpdateGroup.class}) ClassDTO classDTO) {
        adminClassService.modifyClass(classDTO);
        return Result.success();
    }

    @ApiOperation("分类删除接口")
    @DeleteMapping("{classId}")
    public Result<Void> deleteClass(@PathVariable Long classId) {
        adminClassService.deleteClass(classId);
        return Result.success();
    }

    @ApiOperation("分类批量删除接口")
    @DeleteMapping
    public Result<Void> deleteBatchClass(@RequestParam List<Long> ids) {
        if (CollUtil.isNotEmpty(ids)) {
            adminClassService.deleteBatchClass(ids);
        }
        return Result.success();
    }
}
