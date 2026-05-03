package com.libre.controller.admin;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.libre.pojo.dto.admin.MessageDTO;
import com.libre.pojo.dto.admin.MessagePageDTO;
import com.libre.pojo.dto.admin.MessageSendDTO;
import com.libre.pojo.dto.common.UserMessagePageDTO;
import com.libre.pojo.po.Message;
import com.libre.pojo.vo.admin.MessagePageVO;
import com.libre.pojo.vo.admin.MessageVO;
import com.libre.pojo.vo.app.UserMessageDetailVO;
import com.libre.pojo.vo.common.UserMessageVO;
import com.libre.result.PageResult;
import com.libre.result.Result;
import com.libre.service.admin.AdminMessageService;
import com.libre.service.admin.AdminUserMessageService;
import com.libre.validation.UpdateGroup;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.groups.Default;
import java.util.List;

@Api(tags = "消息管理接口")
@RequestMapping("/admin/message")
@RestController
@RequiredArgsConstructor
public class AdminMessageController {
    private final AdminMessageService messageService;

    private final AdminUserMessageService userMessageService;

    @ApiOperation("分页查询管理员消息")
    @GetMapping("/user")
    public Result<PageResult<List<UserMessageVO>>> pageQueryAdminMessage(UserMessagePageDTO userMessagePageDTO) {
        PageResult<List<UserMessageVO>> pageResult = messageService.pageQueryAdminMessage(userMessagePageDTO);
        return Result.success(pageResult);
    }

    @ApiOperation("根据消息id查询用户具体消息")
    @GetMapping("/user/{messageId}")
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

    @ApiOperation("标记全部已读")
    @PutMapping("/read/all")
    public Result<Void> markAllRead() {
        userMessageService.markAllRead();
        return Result.success();
    }

    @ApiOperation("消息分页查询接口")
    @GetMapping
    public Result<PageResult<List<MessagePageVO>>> pageQueryMessage(MessagePageDTO messagePageDTO) {
        PageResult<List<MessagePageVO>> pageResult = messageService.pageQueryMessage(messagePageDTO);
        return Result.success(pageResult);
    }

    @ApiOperation("获取指定消息信息")
    @GetMapping("{messageId}")
    public Result<MessageVO> getMessage(@PathVariable Long messageId) {
        Message message = messageService.getById(messageId);
        MessageVO messageVO = BeanUtil.copyProperties(message, MessageVO.class);
        return Result.success(messageVO);
    }

    @ApiOperation("消息添加接口")
    @PostMapping
    public Result<Void> addMessage(@RequestBody @Valid MessageDTO messageDTO) {
        messageService.addMessage(messageDTO);
        return Result.success();
    }

    @ApiOperation("消息修改接口")
    @PutMapping
    public Result<Void> modifyMessage(@RequestBody @Validated({Default.class, UpdateGroup.class}) MessageDTO messageDTO) {
        messageService.modifyMessage(messageDTO);
        return Result.success();
    }

    @ApiOperation("消息删除接口")
    @DeleteMapping("{messageId}")
    public Result<Void> deleteMessage(@PathVariable Long messageId) {
        messageService.deleteMessage(messageId);
        return Result.success();
    }

    @ApiOperation("消息批量删除接口")
    @DeleteMapping
    public Result<Void> deleteBatchMessage(@RequestParam List<Long> ids) {
        if (CollUtil.isNotEmpty(ids)) {
            messageService.deleteBatchMessage(ids);
        }
        return Result.success();
    }

    @ApiOperation("发送消息")
    @PostMapping("/send")
    public Result<Void> sendMessage(@RequestBody MessageSendDTO messageSendDTO) {
        messageService.sendMessage(messageSendDTO);
        return Result.success();
    }
}
