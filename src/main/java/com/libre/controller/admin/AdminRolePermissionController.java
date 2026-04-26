package com.libre.controller.admin;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.libre.pojo.dto.RolePermissionDTO;
import com.libre.pojo.dto.RolePermissionPageDTO;
import com.libre.pojo.dto.admin.AddRolePermissionDTO;
import com.libre.pojo.po.RolePermission;
import com.libre.pojo.vo.RolePermissionPageVO;
import com.libre.pojo.vo.RolePermissionVO;
import com.libre.result.PageResult;
import com.libre.result.Result;
import com.libre.service.admin.AdminRolePermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "角色权限关联管理接口")
@RequestMapping("/admin/role-permission")
@RestController
@RequiredArgsConstructor
public class AdminRolePermissionController {
    private final AdminRolePermissionService adminRolePermissionService;

    @ApiOperation("角色权限关联分页查询接口")
    @GetMapping
    public Result<PageResult<List<RolePermissionPageVO>>> pageQueryRolePermission(RolePermissionPageDTO rolePermissionPageDTO) {
        PageResult<List<RolePermissionPageVO>> pageResult = adminRolePermissionService.pageQueryRolePermission(rolePermissionPageDTO);
        return Result.success(pageResult);
    }

    @ApiOperation("获取指定角色权限关联信息")
    @GetMapping("{id}")
    public Result<RolePermissionVO> getRolePermission(@PathVariable Long id) {
        RolePermission rolePermission = adminRolePermissionService.getById(id);
        RolePermissionVO rolePermissionVO = BeanUtil.copyProperties(rolePermission, RolePermissionVO.class);
        return Result.success(rolePermissionVO);
    }

    @ApiOperation("角色权限关联添加接口")
    @PostMapping
    public Result<Void> addRolePermission(@RequestBody @Valid RolePermissionDTO rolePermissionDTO) {
        adminRolePermissionService.addRolePermission(rolePermissionDTO);
        return Result.success();
    }

    @ApiOperation("角色权限关联修改接口")
    @PutMapping
    public Result<Void> modifyRolePermission(@RequestBody @Valid RolePermissionDTO rolePermissionDTO) {
        adminRolePermissionService.modifyRolePermission(rolePermissionDTO);
        return Result.success();
    }

    @ApiOperation("角色权限关联删除接口")
    @DeleteMapping("{id}")
    public Result<Void> deleteRolePermission(@PathVariable Long id) {
        adminRolePermissionService.deleteRolePermission(id);
        return Result.success();
    }

    @ApiOperation("角色权限关联批量删除接口")
    @DeleteMapping
    public Result<Void> deleteBatchRolePermission(@RequestParam List<Long> ids) {
        if(CollUtil.isNotEmpty(ids)) {
            adminRolePermissionService.deleteBatchRolePermission(ids);
        }
        return Result.success();
    }
    @ApiOperation("查询指定角色已拥有的权限 id 列表")
    @GetMapping("/role/{roleId}")
    public Result<List<Long>> getRolePermissionIds(@PathVariable Long roleId) {
        List<Long> permissionIds = adminRolePermissionService.getRolePermissionIds(roleId);
        return Result.success(permissionIds);
    }

    @ApiOperation("批量设置指定角色的权限")
    @PostMapping("/assign")
    public Result<Void> assignRolePermission(@RequestBody AddRolePermissionDTO addRolePermissionDTO) {
        adminRolePermissionService.assignRolePermission(addRolePermissionDTO);
        return Result.success();
    }
}
