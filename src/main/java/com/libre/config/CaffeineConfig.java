package com.libre.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class CaffeineConfig {
    @Bean
    public Cache<String,String> captchaCache(){
        return Caffeine.newBuilder()
                // 设置最后一次写入后 1 分钟过期
                .expireAfterWrite(1, TimeUnit.MINUTES)
                // 初始的内存空间大小
                .initialCapacity(100)
                // 缓存的最大条数，防止OOM
                .maximumSize(10000)
                .build();
    }
}
