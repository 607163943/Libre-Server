package com.libre.service.common.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.libre.mapper.UserMessageMapper;
import com.libre.pojo.po.UserMessage;
import com.libre.service.common.CommonUserMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CommonUserMessageServiceImpl extends ServiceImpl<UserMessageMapper, UserMessage> implements CommonUserMessageService {
}
