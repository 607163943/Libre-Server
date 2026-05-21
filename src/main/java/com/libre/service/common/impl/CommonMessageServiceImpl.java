package com.libre.service.common.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.libre.constant.UserMessageState;
import com.libre.enums.ExceptionEnums;
import com.libre.exception.LibreException;
import com.libre.mapper.MessageMapper;
import com.libre.mapper.UserMessageMapper;
import com.libre.pojo.po.Message;
import com.libre.pojo.po.UserMessage;
import com.libre.pojo.vo.common.UserMessageDetailVO;
import com.libre.service.common.CommonMessageService;
import com.libre.service.common.CommonUserMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class CommonMessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements CommonMessageService {
    private final UserMessageMapper userMessageMapper;
    private final CommonUserMessageService userMessageService;

    /**
     * 获取用户消息详情
     * @param messageId 消息id
     * @return 用户消息详情
     */
    @Override
    public UserMessageDetailVO getUserMessageDetail(Long messageId) {
        // 获取当前登录用户ID
        Long userId = StpUtil.getLoginIdAsLong();

        // 查询消息详情
        UserMessageDetailVO messageDetail = userMessageMapper.getUserMessageDetail(messageId, userId);

        if (messageDetail == null) {
            throw new LibreException(ExceptionEnums.MESSAGE_NOT_EXIST);
        }

        UserMessage userMessage = userMessageService.lambdaQuery()
                .eq(UserMessage::getMessageId, messageId)
                .eq(UserMessage::getUserId, userId)
                .one();

        // 查询未读取消息=阅读消息
        if (userMessage.getIsRead().equals(0)) {
            Db.lambdaUpdate(UserMessage.class)
                    .set(UserMessage::getIsRead, UserMessageState.READ)
                    .set(UserMessage::getReadTime, LocalDateTime.now())
                    .eq(UserMessage::getMessageId, messageId)
                    .eq(UserMessage::getUserId, userId)
                    .update();
        }

        return messageDetail;
    }
}
