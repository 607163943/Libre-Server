package com.libre.controller.admin;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.libre.pojo.dto.RoleDTO;
import com.libre.pojo.dto.RolePageDTO;
import com.libre.pojo.po.Role;
import com.libre.pojo.vo.RolePageVO;
import com.libre.pojo.vo.RoleVO;
import com.libre.result.PageResult;
import com.libre.result.Result;
import com.libre.service.admin.AdminRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "角色管理接口")
@RequestMapping("/admin/role")
@RestController
@RequiredArgsConstructor
public class AdminRoleController {
    private final AdminRoleService adminRoleService;

    @ApiOperation("角色分页查询接口")
    @GetMapping
    public Result<PageResult<List<RolePageVO>>> pageQueryRole(RolePageDTO rolePageDTO) {
        PageResult<List<RolePageVO>> pageResult = adminRoleService.pageQueryRole(rolePageDTO);
        return Result.success(pageResult);
    }

    @ApiOperation("获取指定角色信息")
    @GetMapping("{roleId}")
    public Result<RoleVO> getRole(@PathVariable Long roleId) {
        Role role = adminRoleService.getById(roleId);
        RoleVO roleVO = BeanUtil.copyProperties(role, RoleVO.class);
        return Result.success(roleVO);
    }

    @ApiOperation("获取所有角色")
    @GetMapping("all")
    public Result<List<RoleVO>> getAllRoles() {
        List<Role> roleList = adminRoleService.getAllRole();
        List<RoleVO> roleVOList = BeanUtil.copyToList(roleList, RoleVO.class);
        return Result.success(roleVOList);
    }

    @ApiOperation("角色添加接口")
    @PostMapping
    public Result<Void> addRole(@RequestBody @Valid RoleDTO roleDTO) {
        adminRoleService.addRole(roleDTO);
        return Result.success();
    }

    @ApiOperation("角色修改接口")
    @PutMapping
    public Result<Void> modifyRole(@RequestBody @Valid RoleDTO roleDTO) {
        adminRoleService.modifyRole(roleDTO);
        return Result.success();
    }

    @ApiOperation("角色删除接口")
    @DeleteMapping("{roleId}")
    public Result<Void> deleteRole(@PathVariable Long roleId) {
        adminRoleService.deleteRole(roleId);
        return Result.success();
    }

    @ApiOperation("角色批量删除接口")
    @DeleteMapping
    public Result<Void> deleteBatchRole(@RequestParam List<Long> ids) {
        if(CollUtil.isNotEmpty(ids)) {
            adminRoleService.deleteBatchRole(ids);
        }
        return Result.success();
    }
}
