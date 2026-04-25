package com.libre.controller.admin;

import com.libre.pojo.dto.admin.AddUserRoleDTO;
import com.libre.pojo.vo.RoleVO;
import com.libre.result.Result;
import com.libre.service.UserRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "用户角色管理接口")
@RequestMapping("/admin/user-role")
@RestController("admin-user-role-controller")
@RequiredArgsConstructor
public class UserRoleController {
    private final UserRoleService userRoleService;

    @ApiOperation("获取用户拥有角色列表")
    @GetMapping("/user/{userId}")
    public Result<List<RoleVO>> getUserRoles(@PathVariable Long userId) {
        List<RoleVO> roleVOList = userRoleService.getUserRoles(userId);
        return Result.success(roleVOList);
    }

    @ApiOperation("批量设置指定用户的角色")
    @PostMapping("/assign")
    public Result<Void> assignUserRoles(@RequestBody @Valid AddUserRoleDTO addUserRoleDTO) {
        userRoleService.assignUserRoles(addUserRoleDTO);
        return Result.success();
    }
}
