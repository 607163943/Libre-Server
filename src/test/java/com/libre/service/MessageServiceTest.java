package com.libre.service;

import com.libre.service.common.CommonSendMessageService;
import org.elasticsearch.core.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MessageServiceTest {
    @Autowired
    private CommonSendMessageService sendMessageService;
    @Test
    void testSendMessage() {
        sendMessageService.sendLoginSms(List.of("13594377659"), "3467", 1);
    }
}
