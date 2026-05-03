package com.libre.service.app;

import com.baomidou.mybatisplus.extension.service.IService;
import com.libre.pojo.po.UserMessage;

public interface AppUserMessageService extends IService<UserMessage> {
    /**
     * 获取用户未读消息数量
     * @return 未读消息数量
     */
    Long getUserUnreadMessageCount();

    /**
     * 标记所有消息为已读
     */
    void markAllRead();
}
