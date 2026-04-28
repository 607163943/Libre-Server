package com.libre.service;

import cn.hutool.core.lang.UUID;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.BCrypt;
import com.libre.pojo.po.User;
import com.libre.service.common.CommonUserService;
import com.libre.util.SecurityUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class LoginServiceTest {
    @Autowired
    private CommonUserService userService;

    @Autowired
    private SecurityUtil securityUtil;
    @Test
    void testPassword() {
        String mdString = SecureUtil.md5("123456");
        String hash = BCrypt.hashpw(mdString);
        System.out.println(hash);
    }

    @Test
    void testPepper() {
        String string = UUID.fastUUID().toString();
        String pepper = SecureUtil.md5(string);
        System.out.println(pepper);
    }

    @Test
    void initAllUserPassword() {
        List<User> users = userService.list();
        users.forEach(user -> {
            String md5String = SecureUtil.md5("123456");
            user.setPassword(securityUtil.generatePassword(md5String));
        });

        userService.updateBatchById(users);
    }
}
