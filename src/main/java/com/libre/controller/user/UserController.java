package com.libre.controller.user;

import cn.dev33.satoken.stp.StpUtil;
import com.libre.pojo.dto.BasePageDTO;
import com.libre.pojo.dto.user.MyLendPageDTO;
import com.libre.pojo.dto.user.UserPasswordDTO;
import com.libre.pojo.dto.user.UserProfileDTO;
import com.libre.pojo.vo.user.MyLendBookDetailVO;
import com.libre.pojo.vo.user.MyLendHistoryBookVO;
import com.libre.pojo.vo.user.UserProfileVO;
import com.libre.result.PageResult;
import com.libre.result.Result;
import com.libre.service.LendService;
import com.libre.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "用户端用户接口")
@RequiredArgsConstructor
@RequestMapping("/user/user")
@RestController("user-user-controller")
public class UserController {
    private final UserService userService;
    private final LendService lendService;

    @ApiOperation("获取当前用户个人信息")
    @GetMapping("/profile")
    public Result<UserProfileVO> getUserProfile() {
        Long userId = StpUtil.getLoginIdAsLong();
        UserProfileVO userProfileVO = userService.getUserProfile(userId);
        return Result.success(userProfileVO);
    }

    @ApiOperation("修改当前用户个人信息")
    @PutMapping("/profile")
    public Result<Void> modifyUserProfile(@RequestBody @Valid UserProfileDTO userProfileDTO) {
        userService.modifyUserProfile(userProfileDTO);
        return Result.success();
    }

    @ApiOperation("修改当前用户密码")
    @PatchMapping("/profile/password")
    public Result<Void> modifyUserPassword(@RequestBody @Valid UserPasswordDTO userPasswordDTO) {
        userService.modifyUserPassword(userPasswordDTO);
        return Result.success();
    }

    @ApiOperation("分页查询用户借阅书籍")
    @GetMapping("/profile/my-lend")
    public Result<PageResult<List<MyLendBookDetailVO>>> pageQueryMyLend(BasePageDTO basePageDTO) {
        PageResult<List<MyLendBookDetailVO>> pageResult = lendService.pageQueryMyLendDetail(basePageDTO);
        return Result.success(pageResult);
    }

    @ApiOperation("分页查询用户历史借阅书籍")
    @GetMapping("/profile/history")
    public Result<PageResult<List<MyLendHistoryBookVO>>> pageQueryMyLendHistory(BasePageDTO basePageDTO) {
        // 将BasePageDTO转换为MyLendPageDTO
        MyLendPageDTO myLendPageDTO = new MyLendPageDTO();
        myLendPageDTO.setPage(basePageDTO.getPage());
        myLendPageDTO.setPageSize(basePageDTO.getPageSize());
        
        PageResult<List<MyLendHistoryBookVO>> pageResult = lendService.pageQueryMyLendHistory(myLendPageDTO);
        return Result.success(pageResult);
    }
}
