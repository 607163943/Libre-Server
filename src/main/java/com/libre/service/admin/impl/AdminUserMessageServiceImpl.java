package com.libre.service.admin.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.libre.constant.PlatformScope;
import com.libre.constant.UserMessageState;
import com.libre.mapper.UserMessageMapper;
import com.libre.pojo.po.UserMessage;
import com.libre.service.admin.AdminUserMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AdminUserMessageServiceImpl extends ServiceImpl<UserMessageMapper, UserMessage> implements AdminUserMessageService {
    /**
     * 获取用户未读消息数量
     *
     * @return 未读消息数量
     */
    @Override
    public Long getUserUnreadMessageCount() {
        return lambdaQuery()
                .eq(UserMessage::getIsRead, UserMessageState.UNREAD)
                .eq(UserMessage::getReceiverId, StpUtil.getLoginIdAsLong())
                // 仅统计Admin端可查看消息
                .in(UserMessage::getPlatformScope, PlatformScope.ADMIN, PlatformScope.ALL)
                .count();
    }

    /**
     * 标记所有消息为已读
     */
    @Override
    public void markAllRead() {
        lambdaUpdate()
                .set(UserMessage::getIsRead, UserMessageState.READ)
                .eq(UserMessage::getIsRead, UserMessageState.UNREAD)
                .eq(UserMessage::getReceiverId, StpUtil.getLoginIdAsLong())
                // 仅修改App端可查看消息
                .in(UserMessage::getPlatformScope, PlatformScope.ADMIN,PlatformScope.ALL)
                .update();
    }
}
