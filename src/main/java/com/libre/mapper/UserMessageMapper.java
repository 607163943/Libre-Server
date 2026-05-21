package com.libre.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.libre.pojo.dto.common.UserMessagePageDTO;
import com.libre.pojo.po.UserMessage;
import com.libre.pojo.vo.common.UserMessageDetailVO;
import com.libre.pojo.vo.common.UserMessageVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMessageMapper extends BaseMapper<UserMessage> {
    /**
     * 统计指定用户及指定客户端类型的未读消息数量
     * @param userId 用户ID
     * @param clientTypeList 客户端类型列表 (例如: [2, 3] 或 [1])
     * @return 未读数量
     */
    Long countUnreadMessages(@Param("userId") Long userId,
                             @Param("clientTypeList") List<Integer> clientTypeList);

    /**
     * 标记指定用户及指定客户端类型的消息为已读
     * @param userId 用户ID
     * @param clientTypeList 客户端类型列表
     * @return 影响行数
     */
    int markMessagesAsRead(@Param("userId") Long userId,
                           @Param("clientTypeList") List<Integer> clientTypeList);
    /**
     * 分页查询用户消息
     * @param page 分页条件
     * @param userMessagePageDTO 查询参数
     * @return 分页结果
     */
    IPage<UserMessageVO> pageQueryUserMessage(@Param("page") IPage<UserMessageVO> page, @Param("userMessagePageDTO") UserMessagePageDTO userMessagePageDTO);

    /**
     * 根据消息id查询用户具体消息
     * @param messageId 消息id
     * @param userId 用户id
     * @return 消息详情
     */
    UserMessageDetailVO getUserMessageDetail(@Param("messageId") Long messageId, @Param("userId") Long userId);
}