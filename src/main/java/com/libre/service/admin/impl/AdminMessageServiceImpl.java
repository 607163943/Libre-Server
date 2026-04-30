package com.libre.service.admin.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.libre.constant.MessageStats;
import com.libre.mapper.MessageMapper;
import com.libre.pojo.dto.admin.MessageDTO;
import com.libre.pojo.dto.admin.MessagePageDTO;
import com.libre.pojo.dto.admin.MessageSendDTO;
import com.libre.pojo.po.Message;
import com.libre.pojo.vo.admin.MessagePageVO;
import com.libre.result.PageResult;
import com.libre.service.admin.AdminMessageService;
import com.libre.util.PageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AdminMessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements AdminMessageService {

    /**
     * 分页查询消息信息
     *
     * @param messagePageDTO 查询参数
     * @return 分页结果
     */
    @Override
    public PageResult<List<MessagePageVO>> pageQueryMessage(MessagePageDTO messagePageDTO) {
        // 构建分页条件
        IPage<Message> page = PageUtil.createPage(messagePageDTO);
        // 查询
        page = lambdaQuery()
                .like(StrUtil.isNotBlank(messagePageDTO.getTitle()), Message::getTitle, messagePageDTO.getTitle())
                .eq(messagePageDTO.getType() != null, Message::getType, messagePageDTO.getType())
                .page(page);
        // 构建VO数据
        List<MessagePageVO> messagePageVOS = BeanUtil.copyToList(page.getRecords(), MessagePageVO.class);

        return PageResult.<List<MessagePageVO>>builder()
                .total(page.getTotal())
                .data(messagePageVOS)
                .build();
    }

    /**
     * 添加消息信息
     *
     * @param messageDTO 消息信息
     */
    @Override
    public void addMessage(MessageDTO messageDTO) {
        Message message = BeanUtil.copyProperties(messageDTO, Message.class);
        // 避免前端id残留数据影响
        if (message.getId() != null) message.setId(null);
        // 设置创建时间
        message.setCreateTime(LocalDateTime.now());
        // 设置更新时间
        message.setUpdateTime(LocalDateTime.now());
        save(message);
    }

    /**
     * 修改消息信息
     *
     * @param messageDTO 消息信息
     */
    @Override
    public void modifyMessage(MessageDTO messageDTO) {
        Message message = BeanUtil.copyProperties(messageDTO, Message.class);
        // 设置更新时间
        message.setUpdateTime(LocalDateTime.now());
        updateById(message);
    }

    /**
     * 删除消息信息
     *
     * @param messageId 消息id
     */
    @Override
    public void deleteMessage(Long messageId) {
        lambdaUpdate()
                // 使用时间戳标记逻辑删除，避免唯一键冲突
                .set(Message::getIsDelete, System.currentTimeMillis())
                .eq(Message::getId, messageId)
                .update();
    }

    /**
     * 批量删除消息信息
     * @param ids 消息id集合
     */
    @Override
    public void deleteBatchMessage(List<Long> ids) {
        lambdaUpdate()
                .set(Message::getIsDelete, System.currentTimeMillis())
                .in(Message::getId, ids)
                .update();
    }

    /**
     * 发送消息
     * @param messageSendDTO 消息发送DTO
     */
    @Override
    public void sendMessage(MessageSendDTO messageSendDTO) {
        // TODO：需要使用SpringEvent发送事件，由监听器处理
        // 更新消息状态为已发送
        lambdaUpdate()
                .set(Message::getState, MessageStats.SEND)
                .eq(Message::getId, messageSendDTO.getId())
                .update();
    }
}
