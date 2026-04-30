package com.libre.service.app.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.libre.constant.UserMessageState;
import com.libre.enums.ExceptionEnums;
import com.libre.exception.LibreException;
import com.libre.mapper.MessageMapper;
import com.libre.mapper.UserMessageMapper;
import com.libre.pojo.dto.app.UserMessagePageDTO;
import com.libre.pojo.po.Message;
import com.libre.pojo.po.UserMessage;
import com.libre.pojo.vo.app.UserMessageDetailVO;
import com.libre.pojo.vo.app.UserMessageVO;
import com.libre.result.PageResult;
import com.libre.service.app.AppMessageService;
import com.libre.service.app.AppUserMessageService;
import com.libre.util.PageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AppMessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements AppMessageService {
    private final UserMessageMapper userMessageMapper;

    private final AppUserMessageService userMessageService;

    /**
     * 分页查询用户消息
     *
     * @param userMessagePageDTO 查询参数
     * @return 分页结果
     */
    @Override
    public PageResult<List<UserMessageVO>> pageQueryUserMessage(UserMessagePageDTO userMessagePageDTO) {
        // 构建分页条件
        IPage<UserMessageVO> page = PageUtil.createPage(userMessagePageDTO);

        // 设置当前登录用户ID
        Long userId = StpUtil.getLoginIdAsLong();
        userMessagePageDTO.setUserId(userId);
        
        // 查询
        page = userMessageMapper.pageQueryUserMessage(page, userMessagePageDTO);
        
        return PageResult.<List<UserMessageVO>>builder()
                .total(page.getTotal())
                .data(page.getRecords())
                .build();
    }

    /**
     * 根据消息id查询用户具体消息
     *
     * @param messageId 消息id
     * @return 消息详情
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
                .eq(UserMessage::getReceiverId, userId)
                .one();

        // 查询未读取消息=阅读消息
        if(userMessage.getIsRead().equals(0)) {
            Db.lambdaUpdate(UserMessage.class)
                    .set(UserMessage::getIsRead, UserMessageState.READ)
                    .set(UserMessage::getReadTime, LocalDateTime.now())
                    .eq(UserMessage::getMessageId, messageId)
                    .eq(UserMessage::getReceiverId,userId)
                    .update();
        }
        
        return messageDetail;
    }
}