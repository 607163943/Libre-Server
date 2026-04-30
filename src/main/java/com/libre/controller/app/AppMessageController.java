package com.libre.controller.app;

import com.libre.pojo.dto.app.UserMessagePageDTO;
import com.libre.pojo.vo.app.UserMessageDetailVO;
import com.libre.pojo.vo.app.UserMessageVO;
import com.libre.result.PageResult;
import com.libre.result.Result;
import com.libre.service.app.AppMessageService;
import com.libre.service.app.AppUserMessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 用户端消息管理接口
 */
@Api(tags = "用户端消息管理接口")
@RequestMapping("/app/message")
@RestController
@RequiredArgsConstructor
public class AppMessageController {
    private final AppMessageService messageService;
    private final AppUserMessageService userMessageService;

    @ApiOperation("分页查询用户消息")
    @GetMapping
    public Result<PageResult<List<UserMessageVO>>> pageQueryUserMessage(UserMessagePageDTO userMessagePageDTO) {
        PageResult<List<UserMessageVO>> pageResult = messageService.pageQueryUserMessage(userMessagePageDTO);
        return Result.success(pageResult);
    }

    @ApiOperation("根据消息id查询用户具体消息")
    @GetMapping("/{messageId}")
    public Result<UserMessageDetailVO> getUserMessageDetail(@PathVariable Long messageId) {
        UserMessageDetailVO messageDetail = messageService.getUserMessageDetail(messageId);
        return Result.success(messageDetail);
    }

    @ApiOperation("获取用户未查看消息数量")
    @GetMapping("/user/unread")
    public Result<Long> getUserUnreadMessageCount() {
        Long count = userMessageService.getUserUnreadMessageCount();
        return Result.success(count);
    }
}