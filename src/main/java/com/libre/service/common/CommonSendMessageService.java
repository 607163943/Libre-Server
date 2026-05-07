package com.libre.service.common;

import java.util.List;

public interface CommonSendMessageService {
    /**
     * 发送登录验证码
     * @param phoneList 接收手机号集合
     * @param code  验证码内容
     * @param duration 有效时间（分钟）
     */
    void sendLoginSms(List<String> phoneList, String code, int duration);
}
