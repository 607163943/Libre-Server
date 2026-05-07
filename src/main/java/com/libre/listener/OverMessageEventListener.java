package com.libre.listener;

import com.libre.constant.MessageState;
import com.libre.constant.MessageType;
import com.libre.constant.PlatformScope;
import com.libre.pojo.dto.common.OverMessageInfo;
import com.libre.pojo.event.BatchOverMessageEvent;
import com.libre.pojo.po.Message;
import com.libre.pojo.po.UserMessage;
import com.libre.service.common.CommonMessageService;
import com.libre.service.common.CommonUserMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Component
public class OverMessageEventListener {
    private final CommonUserMessageService userMessageService;
    private final CommonMessageService messageService;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener
    public void handleBatchOverdueEvent(BatchOverMessageEvent batchOverMessageEvent) {
        log.info("开始批量通知逾期消息");
        List<OverMessageInfo> overMessageInfos = batchOverMessageEvent.getOverMessageInfos();

        List<Message> messages = overMessageInfos.stream().map(info -> {
            Message m = new Message();
            m.setTitle("图书逾期提醒");
            m.setContent("您的《" + info.getBookName() + "》已逾期" + info.getOverDay() + "天");
            m.setType(MessageType.LEND);
            m.setState(MessageState.SEND);
            // 0表示系统发送
            m.setCreateUserId(0L);
            return m;
        }).collect(Collectors.toList());

        messageService.saveBatch(messages); // 批量插入 tb_message

        // 2. 批量构建 tb_user_message
        List<UserMessage> userMessages = new ArrayList<>();
        for (int i = 0; i < messages.size(); i++) {
            UserMessage um = new UserMessage();
            um.setMessageId(messages.get(i).getId());
            um.setReceiverId(overMessageInfos.get(i).getUserId());
            um.setIsRead(0);
            // 用户逾期借阅信息仅App可见
            um.setPlatformScope(PlatformScope.APP);
            userMessages.add(um);
        }

        userMessageService.saveBatch(userMessages); // 批量插入 tb_user_message
    }
}
