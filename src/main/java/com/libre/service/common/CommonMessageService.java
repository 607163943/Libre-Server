package com.libre.service.common;

import com.baomidou.mybatisplus.extension.service.IService;
import com.libre.pojo.po.Message;
import com.libre.pojo.vo.common.UserMessageDetailVO;

public interface CommonMessageService extends IService<Message> {
    /**
     * 根据消息id查询用户具体消息
     * @param messageId 消息id
     * @return 消息详情
     */
    UserMessageDetailVO getUserMessageDetail(Long messageId);
}
