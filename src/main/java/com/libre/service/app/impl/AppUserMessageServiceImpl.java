package com.libre.service.app.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.libre.mapper.UserMessageMapper;
import com.libre.pojo.po.UserMessage;
import com.libre.service.app.AppUserMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AppUserMessageServiceImpl extends ServiceImpl<UserMessageMapper, UserMessage> implements AppUserMessageService {
}
