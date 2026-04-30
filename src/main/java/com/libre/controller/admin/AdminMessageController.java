package com.libre.controller.admin;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.libre.pojo.dto.admin.MessageDTO;
import com.libre.pojo.dto.admin.MessagePageDTO;
import com.libre.pojo.dto.admin.MessageSendDTO;
import com.libre.pojo.po.Message;
import com.libre.pojo.vo.admin.MessagePageVO;
import com.libre.pojo.vo.admin.MessageVO;
import com.libre.result.PageResult;
import com.libre.result.Result;
import com.libre.service.admin.AdminMessageService;
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
    private final AdminMessageService adminMessageService;

    @ApiOperation("消息分页查询接口")
    @GetMapping
    public Result<PageResult<List<MessagePageVO>>> pageQueryMessage(@Valid MessagePageDTO messagePageDTO) {
        PageResult<List<MessagePageVO>> pageResult = adminMessageService.pageQueryMessage(messagePageDTO);
        return Result.success(pageResult);
    }

    @ApiOperation("获取指定消息信息")
    @GetMapping("{messageId}")
    public Result<MessageVO> getMessage(@PathVariable Long messageId) {
        Message message = adminMessageService.getById(messageId);
        MessageVO messageVO = BeanUtil.copyProperties(message, MessageVO.class);
        return Result.success(messageVO);
    }

    @ApiOperation("消息添加接口")
    @PostMapping
    public Result<Void> addMessage(@RequestBody @Valid MessageDTO messageDTO) {
        adminMessageService.addMessage(messageDTO);
        return Result.success();
    }

    @ApiOperation("消息修改接口")
    @PutMapping
    public Result<Void> modifyMessage(@RequestBody @Validated({Default.class, UpdateGroup.class}) MessageDTO messageDTO) {
        adminMessageService.modifyMessage(messageDTO);
        return Result.success();
    }

    @ApiOperation("消息删除接口")
    @DeleteMapping("{messageId}")
    public Result<Void> deleteMessage(@PathVariable Long messageId) {
        adminMessageService.deleteMessage(messageId);
        return Result.success();
    }

    @ApiOperation("消息批量删除接口")
    @DeleteMapping
    public Result<Void> deleteBatchMessage(@RequestParam List<Long> ids) {
        if (CollUtil.isNotEmpty(ids)) {
            adminMessageService.deleteBatchMessage(ids);
        }
        return Result.success();
    }

    @ApiOperation("发送消息")
    @PostMapping("/send")
    public Result<Void> sendMessage(@RequestBody MessageSendDTO messageSendDTO) {
        adminMessageService.sendMessage(messageSendDTO);
        return Result.success();
    }
}
