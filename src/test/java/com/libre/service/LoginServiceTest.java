package com.libre.service;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.BCrypt;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class LoginServiceTest {
    @Test
    void testPassword() {
        String mdString = SecureUtil.md5("123456");
        String hash = BCrypt.hashpw(mdString);
        System.out.println(hash);
    }
}
