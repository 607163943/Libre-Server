package com.libre.controller.app;

import cn.dev33.satoken.stp.StpUtil;
import com.libre.pojo.dto.common.BasePageDTO;
import com.libre.pojo.dto.app.MyLendPageDTO;
import com.libre.pojo.dto.app.UserPasswordDTO;
import com.libre.pojo.dto.app.UserProfileDTO;
import com.libre.pojo.vo.app.MyLendBookDetailVO;
import com.libre.pojo.vo.app.MyLendHistoryBookVO;
import com.libre.pojo.vo.app.UserProfileVO;
import com.libre.result.PageResult;
import com.libre.result.Result;
import com.libre.service.app.AppLendService;
import com.libre.service.app.AppUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "用户端用户接口")
@RequiredArgsConstructor
@RequestMapping("/app/user")
@RestController
public class AppUserController {
    private final AppUserService appUserService;
    private final AppLendService appLendService;

    @ApiOperation("获取当前用户个人信息")
    @GetMapping("/profile")
    public Result<UserProfileVO> getUserProfile() {
        Long userId = StpUtil.getLoginIdAsLong();
        UserProfileVO userProfileVO = appUserService.getUserProfile(userId);
        return Result.success(userProfileVO);
    }

    @ApiOperation("修改当前用户个人信息")
    @PutMapping("/profile")
    public Result<Void> modifyUserProfile(@RequestBody @Valid UserProfileDTO userProfileDTO) {
        appUserService.modifyUserProfile(userProfileDTO);
        return Result.success();
    }

    @ApiOperation("修改当前用户密码")
    @PatchMapping("/profile/password")
    public Result<Void> modifyUserPassword(@RequestBody @Valid UserPasswordDTO userPasswordDTO) {
        appUserService.modifyUserPassword(userPasswordDTO);
        return Result.success();
    }

    @ApiOperation("分页查询用户借阅书籍")
    @GetMapping("/profile/my-lend")
    public Result<PageResult<List<MyLendBookDetailVO>>> pageQueryMyLend(BasePageDTO basePageDTO) {
        PageResult<List<MyLendBookDetailVO>> pageResult = appLendService.pageQueryMyLendDetail(basePageDTO);
        return Result.success(pageResult);
    }

    @ApiOperation("分页查询用户历史借阅书籍")
    @GetMapping("/profile/history")
    public Result<PageResult<List<MyLendHistoryBookVO>>> pageQueryMyLendHistory(BasePageDTO basePageDTO) {
        // 将BasePageDTO转换为MyLendPageDTO
        MyLendPageDTO myLendPageDTO = new MyLendPageDTO();
        myLendPageDTO.setPage(basePageDTO.getPage());
        myLendPageDTO.setPageSize(basePageDTO.getPageSize());
        
        PageResult<List<MyLendHistoryBookVO>> pageResult = appLendService.pageQueryMyLendHistory(myLendPageDTO);
        return Result.success(pageResult);
    }
}
