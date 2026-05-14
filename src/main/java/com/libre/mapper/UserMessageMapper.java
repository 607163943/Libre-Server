package com.libre.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.libre.pojo.dto.common.UserMessagePageDTO;
import com.libre.pojo.po.UserMessage;
import com.libre.pojo.vo.common.UserMessageDetailVO;
import com.libre.pojo.vo.common.UserMessageVO;
import org.apache.ibatis.annotations.Param;

public interface UserMessageMapper extends BaseMapper<UserMessage> {
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