package com.libre.service.common.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.libre.mapper.MessageMapper;
import com.libre.pojo.po.Message;
import com.libre.service.common.CommonMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CommonMessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements CommonMessageService {
}
