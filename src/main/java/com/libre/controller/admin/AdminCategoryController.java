package com.libre.controller.admin;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.libre.pojo.dto.admin.CategoryDTO;
import com.libre.pojo.dto.admin.CategoryPageDTO;
import com.libre.pojo.po.Category;
import com.libre.pojo.vo.admin.CategoryPageVO;
import com.libre.pojo.vo.admin.CategoryVO;
import com.libre.result.PageResult;
import com.libre.result.Result;
import com.libre.service.admin.AdminCategoryService;
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
@RequestMapping("/admin/category")
@RestController
@RequiredArgsConstructor
public class AdminCategoryController {
    private final AdminCategoryService categoryService;

    @ApiOperation("分类分页查询接口")
    @GetMapping
    public Result<PageResult<List<CategoryPageVO>>> pageQueryCategory(CategoryPageDTO categoryPageDTO) {
        PageResult<List<CategoryPageVO>> pageResult = categoryService.pageQueryClass(categoryPageDTO);
        return Result.success(pageResult);
    }

    @ApiOperation("获取指定分类信息")
    @GetMapping("/{categoryId}")
    public Result<CategoryVO> getClass(@PathVariable Long categoryId) {
        Category clazz = categoryService.getById(categoryId);
        CategoryVO categoryVO = BeanUtil.copyProperties(clazz, CategoryVO.class);
        return Result.success(categoryVO);
    }

    @ApiOperation("获取所有分类")
    @GetMapping("/all")
    public Result<List<CategoryVO>> getAllClasses() {
        List<Category> categoryList = categoryService.getAllClass();
        List<CategoryVO> categoryVOList = BeanUtil.copyToList(categoryList, CategoryVO.class);
        return Result.success(categoryVOList);
    }

    @ApiOperation("分类添加接口")
    @PostMapping
    public Result<Void> addClass(@RequestBody @Valid CategoryDTO categoryDTO) {
        categoryService.addClass(categoryDTO);
        return Result.success();
    }

    @ApiOperation("分类修改接口")
    @PutMapping
    public Result<Void> modifyClass(@RequestBody @Validated({Default.class, UpdateGroup.class}) CategoryDTO categoryDTO) {
        categoryService.modifyClass(categoryDTO);
        return Result.success();
    }

    @ApiOperation("分类删除接口")
    @DeleteMapping("/{classId}")
    public Result<Void> deleteClass(@PathVariable Long classId) {
        categoryService.deleteClass(classId);
        return Result.success();
    }

    @ApiOperation("分类批量删除接口")
    @DeleteMapping
    public Result<Void> deleteBatchClass(@RequestParam List<Long> ids) {
        if (CollUtil.isNotEmpty(ids)) {
            categoryService.deleteBatchClass(ids);
        }
        return Result.success();
    }
}
