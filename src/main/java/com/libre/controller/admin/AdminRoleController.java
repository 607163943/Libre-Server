package com.libre.controller.admin;

import cn.hutool.core.bean.BeanUtil;
import com.libre.pojo.po.Role;
import com.libre.pojo.vo.admin.RoleVO;
import com.libre.result.Result;
import com.libre.service.admin.AdminRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@Api(tags = "角色管理接口")
@RequestMapping("/admin/role")
@RestController
@RequiredArgsConstructor
public class AdminRoleController {
    private final AdminRoleService adminRoleService;

    @ApiOperation("获取所有角色")
    @GetMapping("all")
    public Result<List<RoleVO>> getAllRoles() {
        List<Role> roleList = adminRoleService.getAllRole();
        List<RoleVO> roleVOList = BeanUtil.copyToList(roleList, RoleVO.class);
        return Result.success(roleVOList);
    }
}
