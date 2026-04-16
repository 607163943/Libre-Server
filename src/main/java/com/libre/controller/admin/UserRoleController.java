package com.libre.controller.admin;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.libre.pojo.dto.UserRoleDTO;
import com.libre.pojo.dto.UserRolePageDTO;
import com.libre.pojo.po.UserRole;
import com.libre.pojo.vo.UserRolePageVO;
import com.libre.pojo.vo.UserRoleVO;
import com.libre.result.PageResult;
import com.libre.result.Result;
import com.libre.service.UserRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "用户角色管理接口")
@RequestMapping("/admin/user-role")
@RestController
@RequiredArgsConstructor
public class UserRoleController {
    private final UserRoleService userRoleService;

    @ApiOperation("用户角色分页查询接口")
    @GetMapping
    public Result<PageResult<List<UserRolePageVO>>> pageQueryUserRole(UserRolePageDTO userRolePageDTO) {
        PageResult<List<UserRolePageVO>> pageResult = userRoleService.pageQueryUserRole(userRolePageDTO);
        return Result.success(pageResult);
    }

    @ApiOperation("获取指定用户角色信息")
    @GetMapping("{userRoleId}")
    public Result<UserRoleVO> getUserRole(@PathVariable Long userRoleId) {
        UserRole userRole = userRoleService.getById(userRoleId);
        UserRoleVO userRoleVO = BeanUtil.copyProperties(userRole, UserRoleVO.class);
        return Result.success(userRoleVO);
    }

    @ApiOperation("获取所有用户角色")
    @GetMapping("all")
    public Result<List<UserRoleVO>> getAllUserRoles() {
        List<UserRole> userRoleList = userRoleService.list();
        List<UserRoleVO> userRoleVOList = BeanUtil.copyToList(userRoleList, UserRoleVO.class);
        return Result.success(userRoleVOList);
    }

    @ApiOperation("用户角色添加接口")
    @PostMapping
    public Result<Void> addUserRole(@RequestBody UserRoleDTO userRoleDTO) {
        userRoleService.addUserRole(userRoleDTO);
        return Result.success();
    }

    @ApiOperation("用户角色修改接口")
    @PutMapping
    public Result<Void> modifyUserRole(@RequestBody UserRoleDTO userRoleDTO) {
        userRoleService.modifyUserRole(userRoleDTO);
        return Result.success();
    }

    @ApiOperation("用户角色删除接口")
    @DeleteMapping("{userRoleId}")
    public Result<Void> deleteUserRole(@PathVariable Long userRoleId) {
        userRoleService.deleteUserRole(userRoleId);
        return Result.success();
    }

    @ApiOperation("用户角色批量删除接口")
    @DeleteMapping
    public Result<Void> deleteBatchUserRole(@RequestParam List<Long> ids) {
        if(CollUtil.isNotEmpty(ids)) {
            userRoleService.deleteBatchUserRole(ids);
        }
        return Result.success();
    }
}
