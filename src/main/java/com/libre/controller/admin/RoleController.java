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
import com.libre.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "角色管理接口")
@RequestMapping("/admin/role")
@RestController("admin-role-controller")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;

    @ApiOperation("角色分页查询接口")
    @GetMapping
    public Result<PageResult<List<RolePageVO>>> pageQueryRole(RolePageDTO rolePageDTO) {
        PageResult<List<RolePageVO>> pageResult = roleService.pageQueryRole(rolePageDTO);
        return Result.success(pageResult);
    }

    @ApiOperation("获取指定角色信息")
    @GetMapping("{roleId}")
    public Result<RoleVO> getRole(@PathVariable Long roleId) {
        Role role = roleService.getById(roleId);
        RoleVO roleVO = BeanUtil.copyProperties(role, RoleVO.class);
        return Result.success(roleVO);
    }

    @ApiOperation("获取所有角色")
    @GetMapping("all")
    public Result<List<RoleVO>> getAllRoles() {
        List<Role> roleList = roleService.list();
        List<RoleVO> roleVOList = BeanUtil.copyToList(roleList, RoleVO.class);
        return Result.success(roleVOList);
    }

    @ApiOperation("角色添加接口")
    @PostMapping
    public Result<Void> addRole(@RequestBody @Valid RoleDTO roleDTO) {
        roleService.addRole(roleDTO);
        return Result.success();
    }

    @ApiOperation("角色修改接口")
    @PutMapping
    public Result<Void> modifyRole(@RequestBody @Valid RoleDTO roleDTO) {
        roleService.modifyRole(roleDTO);
        return Result.success();
    }

    @ApiOperation("角色删除接口")
    @DeleteMapping("{roleId}")
    public Result<Void> deleteRole(@PathVariable Long roleId) {
        roleService.deleteRole(roleId);
        return Result.success();
    }

    @ApiOperation("角色批量删除接口")
    @DeleteMapping
    public Result<Void> deleteBatchRole(@RequestParam List<Long> ids) {
        if(CollUtil.isNotEmpty(ids)) {
            roleService.deleteBatchRole(ids);
        }
        return Result.success();
    }
}
