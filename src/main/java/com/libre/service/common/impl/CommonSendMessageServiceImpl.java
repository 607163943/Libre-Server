package com.libre.service.common.impl;

import com.libre.enums.ExceptionEnums;
import com.libre.exception.MessageException;
import com.libre.result.SendMessageResult;
import com.libre.service.common.CommonSendMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommonSendMessageServiceImpl implements CommonSendMessageService {
    // 建议配置在 application.yml 中
    @Value("${sms.url}")
    private String API_URL;

    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * 发送登录验证码
     *
     * @param phoneList 接收手机号集合
     * @param code      验证码内容
     * @param duration  有效时间（分钟）
     */
    @Async("smsExecutor")
    public void sendLoginSms(List<String> phoneList, String code, int duration) {
        log.warn("开始异步执行，当前线程名称: {}", Thread.currentThread().getName());
        log.info("开始发送短信验证码: {}", phoneList);
        // 多个手机号用英文逗号分隔
        String phones = String.join(",", phoneList);
        // 1. 构建请求体 (对应模板中的 ${name}, ${code}, ${number})
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("code", code);         // 对应模板变量 ${code}
        requestBody.put("number", duration);   // 对应模板变量 ${number}
        requestBody.put("to", phones);          // 接收手机号

        try {
            // 2. 发送 POST 请求
            restTemplate.postForObject(API_URL, requestBody, SendMessageResult.class);
            log.info("短信发送成功: {}", phoneList);
        } catch (Exception e) {
            log.error("短信发送失败: ", e);
            log.error("发送手机号phone:{}", phones);
            throw new MessageException(ExceptionEnums.MESSAGE_SEND_ERROR);
        }
    }
}
