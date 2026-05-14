package com.libre.controller.admin;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.libre.pojo.dto.admin.UserDTO;
import com.libre.pojo.dto.admin.UserPageDTO;
import com.libre.pojo.dto.common.UserPasswordDTO;
import com.libre.pojo.dto.common.UserProfileDTO;
import com.libre.pojo.po.User;
import com.libre.pojo.vo.admin.UserPageVO;
import com.libre.pojo.vo.admin.UserVO;
import com.libre.pojo.vo.common.UserProfileVO;
import com.libre.result.PageResult;
import com.libre.result.Result;
import com.libre.service.admin.AdminUserService;
import com.libre.service.common.CommonUserService;
import com.libre.validation.UpdateGroup;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.groups.Default;
import java.util.List;

@Api(tags = "用户管理接口")
@RequestMapping("/admin/user")
@RestController
@RequiredArgsConstructor
public class AdminUserController {
    private final AdminUserService userService;

    private final CommonUserService commonUserService;

    @ApiOperation("用户分页查询接口")
    @GetMapping
    public Result<PageResult<List<UserPageVO>>> pageQueryUser(UserPageDTO userPageDTO) {
        PageResult<List<UserPageVO>> pageResult = userService.pageQueryUser(userPageDTO);
        return Result.success(pageResult);
    }

    @ApiOperation("获取指定用户信息")
    @GetMapping("/{userId}")
    public Result<UserVO> getUser(@PathVariable Long userId) {
        User user = userService.getById(userId);
        UserVO userVO = BeanUtil.copyProperties(user, UserVO.class);
        return Result.success(userVO);
    }

    @ApiOperation("获取所有用户")
    @GetMapping("all")
    public Result<List<UserVO>> getAllUsers() {
        List<User> userList = userService.list();
        List<UserVO> userVOList = BeanUtil.copyToList(userList, UserVO.class);
        return Result.success(userVOList);
    }

    @ApiOperation("用户添加接口")
    @PostMapping
    public Result<Void> addUser(@RequestBody @Valid UserDTO userDTO) {
        userService.addUser(userDTO);
        return Result.success();
    }

    @ApiOperation("用户修改接口")
    @PutMapping
    public Result<Void> modifyUser(@RequestBody @Validated({Default.class, UpdateGroup.class}) UserDTO userDTO) {
        userService.modifyUser(userDTO);
        return Result.success();
    }

    @ApiOperation("用户删除接口")
    @DeleteMapping("{userId}")
    public Result<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return Result.success();
    }

    @ApiOperation("用户批量删除接口")
    @DeleteMapping("batch")
    public Result<Void> deleteBatchUser(@RequestParam List<Long> ids) {
        if(CollUtil.isNotEmpty(ids)) {
            userService.deleteBatchUser(ids);
        }
        return Result.success();
    }

    @ApiOperation("修改用户状态")
    @PatchMapping("/{userId}/state/{state}")
    public Result<Void> modifyUserState(@PathVariable Long userId, @PathVariable Integer state) {
        userService.modifyUserState(userId, state);
        return Result.success();
    }

    @ApiOperation("获取指定用户个人信息")
    @GetMapping("profile")
    public Result<UserProfileVO> getUserProfile() {
        UserProfileVO userProfileVO = commonUserService.getUserProfile();
        return Result.success(userProfileVO);
    }

    @ApiOperation("修改指定用户个人信息")
    @PutMapping("profile")
    public Result<Void> modifyUserProfile(@RequestBody @Valid UserProfileDTO userProfileDTO) {
        commonUserService.modifyUserProfile(userProfileDTO);
        return Result.success();
    }

    @ApiOperation("修改指定用户密码")
    @PatchMapping("/profile/password")
    public Result<Void> modifyUserPassword(@RequestBody @Valid UserPasswordDTO userPasswordDTO) {
        commonUserService.modifyUserPassword(userPasswordDTO);
        return Result.success();
    }
}
