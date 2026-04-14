package com.libre.controller.admin;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.libre.pojo.dto.UserDTO;
import com.libre.pojo.dto.UserPageDTO;
import com.libre.pojo.po.User;
import com.libre.pojo.vo.UserPageVO;
import com.libre.pojo.vo.UserVO;
import com.libre.result.PageResult;
import com.libre.result.Result;
import com.libre.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "用户管理接口")
@RequestMapping("/user")
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @ApiOperation("用户分页查询接口")
    @GetMapping
    public Result<PageResult<List<UserPageVO>>> pageQueryUser(UserPageDTO userPageDTO) {
        PageResult<List<UserPageVO>> pageResult = userService.pageQueryUser(userPageDTO);
        return Result.success(pageResult);
    }

    @ApiOperation("获取指定用户信息")
    @GetMapping("{userId}")
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
    public Result<Void> addUser(@RequestBody UserDTO userDTO) {
        userService.addUser(userDTO);
        return Result.success();
    }

    @ApiOperation("用户修改接口")
    @PutMapping
    public Result<Void> modifyUser(@RequestBody UserDTO userDTO) {
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
    @DeleteMapping
    public Result<Void> deleteBatchUser(@RequestParam List<Long> ids) {
        if(CollUtil.isNotEmpty(ids)) {
            userService.deleteBatchUser(ids);
        }
        return Result.success();
    }
}
