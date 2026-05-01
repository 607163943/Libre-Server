package com.libre.service.app;

import com.baomidou.mybatisplus.extension.service.IService;
import com.libre.pojo.dto.common.UserMessagePageDTO;
import com.libre.pojo.po.Message;
import com.libre.pojo.vo.app.UserMessageDetailVO;
import com.libre.pojo.vo.common.UserMessageVO;
import com.libre.result.PageResult;

import java.util.List;

public interface AppMessageService extends IService<Message> {
    /**
     * 分页查询用户消息
     * @param userMessagePageDTO 查询参数
     * @return 分页结果
     */
    PageResult<List<UserMessageVO>> pageQueryUserMessage(UserMessagePageDTO userMessagePageDTO);

    /**
     * 根据消息id查询用户具体消息
     * @param messageId 消息id
     * @return 消息详情
     */
    UserMessageDetailVO getUserMessageDetail(Long messageId);
}