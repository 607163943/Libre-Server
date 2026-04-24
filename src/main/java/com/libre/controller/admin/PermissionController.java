package com.libre.controller.admin;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.libre.pojo.dto.PermissionDTO;
import com.libre.pojo.dto.PermissionPageDTO;
import com.libre.pojo.po.Permission;
import com.libre.pojo.vo.PermissionPageVO;
import com.libre.pojo.vo.PermissionVO;
import com.libre.result.PageResult;
import com.libre.result.Result;
import com.libre.service.PermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "权限管理接口")
@RequestMapping("/admin/permission")
@RestController("admin-permission-controller")
@RequiredArgsConstructor
public class PermissionController {
    private final PermissionService permissionService;

    @ApiOperation("权限分页查询接口")
    @GetMapping
    public Result<PageResult<List<PermissionPageVO>>> pageQueryPermission(PermissionPageDTO permissionPageDTO) {
        PageResult<List<PermissionPageVO>> pageResult = permissionService.pageQueryPermission(permissionPageDTO);
        return Result.success(pageResult);
    }

    @ApiOperation("获取指定权限信息")
    @GetMapping("{permissionId}")
    public Result<PermissionVO> getPermission(@PathVariable Long permissionId) {
        Permission permission = permissionService.getById(permissionId);
        PermissionVO permissionVO = BeanUtil.copyProperties(permission, PermissionVO.class);
        return Result.success(permissionVO);
    }

    @ApiOperation("获取所有权限")
    @GetMapping("all")
    public Result<List<PermissionVO>> getAllPermissions() {
        List<Permission> permissionList = permissionService.getAllPermission();
        List<PermissionVO> permissionVOList = BeanUtil.copyToList(permissionList, PermissionVO.class);
        return Result.success(permissionVOList);
    }

    @ApiOperation("权限添加接口")
    @PostMapping
    public Result<Void> addPermission(@RequestBody @Valid PermissionDTO permissionDTO) {
        permissionService.addPermission(permissionDTO);
        return Result.success();
    }

    @ApiOperation("权限修改接口")
    @PutMapping
    public Result<Void> modifyPermission(@RequestBody @Valid PermissionDTO permissionDTO) {
        permissionService.modifyPermission(permissionDTO);
        return Result.success();
    }

    @ApiOperation("权限删除接口")
    @DeleteMapping("{permissionId}")
    public Result<Void> deletePermission(@PathVariable Long permissionId) {
        permissionService.deletePermission(permissionId);
        return Result.success();
    }

    @ApiOperation("权限批量删除接口")
    @DeleteMapping
    public Result<Void> deleteBatchPermission(@RequestParam List<Long> ids) {
        if (CollUtil.isNotEmpty(ids)) {
            permissionService.deleteBatchPermission(ids);
        }
        return Result.success();
    }
}
