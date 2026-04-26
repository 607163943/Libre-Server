package com.libre.controller.admin;

import com.libre.pojo.dto.admin.AddRolePermissionDTO;
import com.libre.result.Result;
import com.libre.service.admin.AdminRolePermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "角色权限关联管理接口")
@RequestMapping("/admin/role-permission")
@RestController
@RequiredArgsConstructor
public class AdminRolePermissionController {
    private final AdminRolePermissionService adminRolePermissionService;

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
