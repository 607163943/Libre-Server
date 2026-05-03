package com.libre.service.admin.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.libre.constant.MessageState;
import com.libre.constant.PlatformScope;
import com.libre.constant.RoleCode;
import com.libre.constant.UserMessageState;
import com.libre.enums.ExceptionEnums;
import com.libre.exception.LibreException;
import com.libre.mapper.MessageMapper;
import com.libre.mapper.UserMessageMapper;
import com.libre.pojo.dto.admin.MessageDTO;
import com.libre.pojo.dto.admin.MessagePageDTO;
import com.libre.pojo.dto.admin.MessageSendDTO;
import com.libre.pojo.dto.common.UserMessagePageDTO;
import com.libre.pojo.po.Message;
import com.libre.pojo.po.UserMessage;
import com.libre.pojo.po.UserRole;
import com.libre.pojo.vo.admin.MessagePageVO;
import com.libre.pojo.vo.app.UserMessageDetailVO;
import com.libre.pojo.vo.common.UserMessageVO;
import com.libre.result.PageResult;
import com.libre.service.admin.AdminMessageService;
import com.libre.service.admin.AdminUserMessageService;
import com.libre.service.admin.AdminUserRoleService;
import com.libre.util.PageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AdminMessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements AdminMessageService {

    private final AdminUserRoleService userRoleService;

    private final AdminUserMessageService userMessageService;

    private final UserMessageMapper userMessageMapper;

    // 保证事务
    @Lazy
    @Resource
    private AdminMessageService messageService;

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
        // 设置创建用户id
        message.setCreateUserId(StpUtil.getLoginIdAsLong());
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
     *
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
     *
     * @param messageSendDTO 消息发送DTO
     */
    @Transactional
    @Override
    public void sendMessage(MessageSendDTO messageSendDTO) {
        // 获取指定角色的用户群体
        LambdaQueryChainWrapper<UserRole> chainWrapper = userRoleService.lambdaQuery();
        Integer targetNumber = messageSendDTO.getTarget();
        if (!targetNumber.equals(PlatformScope.ALL)) {
            if (targetNumber.equals(PlatformScope.ADMIN)) {
                chainWrapper.in(UserRole::getRoleId, RoleCode.SUPER_ADMIN, RoleCode.ADMIN);
            } else {
                chainWrapper.eq(UserRole::getRoleId, RoleCode.READER);
            }
        }
        List<UserRole> userRoleList = chainWrapper
                .list();

        // 获取用户id集合
        List<Long> targetUserIds = userRoleList.stream().map(UserRole::getUserId).collect(Collectors.toList());
        // 构建消息发送数据
        List<UserMessage> userMessages = new ArrayList<>(targetUserIds.size());
        for (Long targetUserId : targetUserIds) {
            UserMessage userMessage = UserMessage.builder()
                    .receiverId(targetUserId)
                    .messageId(messageSendDTO.getId())
                    .platformScope(targetNumber)
                    .isRead(UserMessageState.UNREAD)
                    .build();
            userMessages.add(userMessage);
        }

        // 批量保存发送消息
        userMessageService.saveBatch(userMessages);
        // 更新消息状态为已发送
        messageService.lambdaUpdate()
                .set(Message::getState, MessageState.SEND)
                .eq(Message::getId, messageSendDTO.getId())
                .update();
    }

    /**
     * 分页查询用户消息
     * @param userMessagePageDTO 查询参数
     * @return 分页结果
     */
    @Override
    public PageResult<List<UserMessageVO>> pageQueryAdminMessage(UserMessagePageDTO userMessagePageDTO) {
        // 构建分页条件
        IPage<UserMessageVO> page = PageUtil.createPage(userMessagePageDTO);

        // 设置当前登录用户ID
        Long userId = StpUtil.getLoginIdAsLong();
        userMessagePageDTO.setUserId(userId);

        // 设置查询端范围
        userMessagePageDTO.setPlatformScopes(new ArrayList<>(Arrays.asList(PlatformScope.ALL, PlatformScope.ADMIN)));

        // 查询
        page = userMessageMapper.pageQueryUserMessage(page, userMessagePageDTO);

        return PageResult.<List<UserMessageVO>>builder()
                .total(page.getTotal())
                .data(page.getRecords())
                .build();
    }

    /**
     * 获取用户消息详情
     * @param messageId 消息id
     * @return 用户消息详情
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
        if (userMessage.getIsRead().equals(0)) {
            Db.lambdaUpdate(UserMessage.class)
                    .set(UserMessage::getIsRead, UserMessageState.READ)
                    .set(UserMessage::getReadTime, LocalDateTime.now())
                    .eq(UserMessage::getMessageId, messageId)
                    .eq(UserMessage::getReceiverId, userId)
                    .update();
        }

        return messageDetail;
    }
}
