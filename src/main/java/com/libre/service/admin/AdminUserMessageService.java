package com.libre.service.admin;

import com.baomidou.mybatisplus.extension.service.IService;
import com.libre.pojo.po.UserMessage;

public interface AdminUserMessageService extends IService<UserMessage> {
    /**
     * 获取用户未读消息数量
     * @return 未读消息数量
     */
    Long getUserUnreadMessageCount();
}
