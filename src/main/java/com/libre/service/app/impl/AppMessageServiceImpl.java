package com.libre.service.app.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.libre.constant.PlatformScope;
import com.libre.mapper.MessageMapper;
import com.libre.mapper.UserMessageMapper;
import com.libre.pojo.dto.common.UserMessagePageDTO;
import com.libre.pojo.po.Message;
import com.libre.pojo.vo.common.UserMessageVO;
import com.libre.result.PageResult;
import com.libre.service.app.AppMessageService;
import com.libre.util.PageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AppMessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements AppMessageService {
    private final UserMessageMapper userMessageMapper;

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

        // 设置查询端范围
        userMessagePageDTO.setPlatformScopes(new ArrayList<>(Arrays.asList(PlatformScope.ALL, PlatformScope.APP)));

        // 查询
        page = userMessageMapper.pageQueryUserMessage(page, userMessagePageDTO);

        return PageResult.<List<UserMessageVO>>builder()
                .total(page.getTotal())
                .data(page.getRecords())
                .build();
    }
}