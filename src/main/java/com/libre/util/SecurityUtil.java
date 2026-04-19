package com.libre.util;

import cn.hutool.crypto.digest.BCrypt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtil {

    @Value("${security.pepper}")
    private String pepper;
    public String generatePassword(String frontendPasswordMd5) {
        String mixString= mix(frontendPasswordMd5, pepper);
        return BCrypt.hashpw(mixString);
    }

    /**
     * 自定义混淆拼接逻辑
     */
    public String mix(String frontendPasswordMd5, String pepper) {
        // 健壮性检查
        if (frontendPasswordMd5.length() < 32) return frontendPasswordMd5 + pepper;

        // 交叉混淆
        // 取前端MD5的前8位 + Pepper的后8位 + 前端MD5的剩余部分 + Pepper的剩余部分
        String part1 = frontendPasswordMd5.substring(0, 8);
        String part2 = pepper.substring(pepper.length() - 8);
        String part3 = frontendPasswordMd5.substring(8);
        String part4 = pepper.substring(0, pepper.length() - 8);

        return part1 + part2 + part3 + part4;
    }

    public boolean checkPassword(String frontendPasswordMd5, String hash) {
        String mixString= mix(frontendPasswordMd5, pepper);
        return BCrypt.checkpw(mixString, hash);
    }
}
