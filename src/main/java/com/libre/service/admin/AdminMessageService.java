package com.libre.service.admin;

import com.baomidou.mybatisplus.extension.service.IService;
import com.libre.pojo.dto.admin.MessageDTO;
import com.libre.pojo.dto.admin.MessagePageDTO;
import com.libre.pojo.dto.admin.MessageSendDTO;
import com.libre.pojo.dto.common.UserMessagePageDTO;
import com.libre.pojo.po.Message;
import com.libre.pojo.vo.admin.MessagePageVO;
import com.libre.pojo.vo.common.UserMessageVO;
import com.libre.result.PageResult;

import java.util.List;

public interface AdminMessageService extends IService<Message> {
    /**
     * 分页查询消息信息
     * @param messagePageDTO 查询参数
     * @return 查询结果
     */
    PageResult<List<MessagePageVO>> pageQueryMessage(MessagePageDTO messagePageDTO);

    /**
     * 添加消息
     * @param messageDTO 消息信息
     */
    void addMessage(MessageDTO messageDTO);

    /**
     * 修改消息
     * @param messageDTO 消息信息
     */
    void modifyMessage(MessageDTO messageDTO);

    /**
     * 删除消息
     * @param messageId 消息id
     */
    void deleteMessage(Long messageId);

    /**
     * 批量删除消息
     * @param ids 消息id集合
     */
    void deleteBatchMessage(List<Long> ids);

    /**
     * 发送消息
     * @param messageSendDTO 消息DTO
     */
    void sendMessage(MessageSendDTO messageSendDTO);

    /**
     * 分页查询用户消息
     * @param userMessagePageDTO 查询参数
     * @return 查询结果
     */
    PageResult<List<UserMessageVO>> pageQueryAdminMessage(UserMessagePageDTO userMessagePageDTO);
}
