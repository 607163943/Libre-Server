package com.libre.controller.admin;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.libre.pojo.dto.RolePermissionDTO;
import com.libre.pojo.dto.RolePermissionPageDTO;
import com.libre.pojo.po.RolePermission;
import com.libre.pojo.vo.RolePermissionPageVO;
import com.libre.pojo.vo.RolePermissionVO;
import com.libre.result.PageResult;
import com.libre.result.Result;
import com.libre.service.RolePermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "角色权限关联管理接口")
@RequestMapping("/admin/role-permission")
@RestController("admin-role-permission-controller")
@RequiredArgsConstructor
public class RolePermissionController {
    private final RolePermissionService rolePermissionService;

    @ApiOperation("角色权限关联分页查询接口")
    @GetMapping
    public Result<PageResult<List<RolePermissionPageVO>>> pageQueryRolePermission(RolePermissionPageDTO rolePermissionPageDTO) {
        PageResult<List<RolePermissionPageVO>> pageResult = rolePermissionService.pageQueryRolePermission(rolePermissionPageDTO);
        return Result.success(pageResult);
    }

    @ApiOperation("获取指定角色权限关联信息")
    @GetMapping("{id}")
    public Result<RolePermissionVO> getRolePermission(@PathVariable Long id) {
        RolePermission rolePermission = rolePermissionService.getById(id);
        RolePermissionVO rolePermissionVO = BeanUtil.copyProperties(rolePermission, RolePermissionVO.class);
        return Result.success(rolePermissionVO);
    }

    @ApiOperation("角色权限关联添加接口")
    @PostMapping
    public Result<Void> addRolePermission(@RequestBody @Valid RolePermissionDTO rolePermissionDTO) {
        rolePermissionService.addRolePermission(rolePermissionDTO);
        return Result.success();
    }

    @ApiOperation("角色权限关联修改接口")
    @PutMapping
    public Result<Void> modifyRolePermission(@RequestBody @Valid RolePermissionDTO rolePermissionDTO) {
        rolePermissionService.modifyRolePermission(rolePermissionDTO);
        return Result.success();
    }

    @ApiOperation("角色权限关联删除接口")
    @DeleteMapping("{id}")
    public Result<Void> deleteRolePermission(@PathVariable Long id) {
        rolePermissionService.deleteRolePermission(id);
        return Result.success();
    }

    @ApiOperation("角色权限关联批量删除接口")
    @DeleteMapping
    public Result<Void> deleteBatchRolePermission(@RequestParam List<Long> ids) {
        if(CollUtil.isNotEmpty(ids)) {
            rolePermissionService.deleteBatchRolePermission(ids);
        }
        return Result.success();
    }
}
