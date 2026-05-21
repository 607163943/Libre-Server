package com.libre.service.admin.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.libre.mapper.UserMessageMapper;
import com.libre.pojo.po.UserMessage;
import com.libre.service.admin.AdminUserMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        List<Integer> clientTypeList = new ArrayList<>(Arrays.asList(2, 3));
        return baseMapper.countUnreadMessages(StpUtil.getLoginIdAsLong(), clientTypeList);
    }

    /**
     * 标记所有消息为已读
     */
    @Override
    public void markAllRead() {
        List<Integer> clientTypeList = new ArrayList<>(Arrays.asList(2, 3));
        baseMapper.markMessagesAsRead(StpUtil.getLoginIdAsLong(), clientTypeList);
    }
}
