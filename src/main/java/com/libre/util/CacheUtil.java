package com.libre.util;

import cn.hutool.core.util.StrUtil;
import com.github.benmanes.caffeine.cache.Cache;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Slf4j
@RequiredArgsConstructor
@Component
public class CacheUtil {
    private final StringRedisTemplate stringRedisTemplate;

    private final Cache<String, String> captchaCache;

    /**
     * 获取缓存值
     *
     * @param key 缓存key
     * @return 缓存值
     */
    public String get(String key) {
        String value=captchaCache.getIfPresent(key);
        if(StrUtil.isNotBlank(value)) {
            return value;
        }

        // Caffeine没有走Redis
        try{
            value=stringRedisTemplate.opsForValue().get(key);
        }catch (Exception e) {
            log.warn("Redis缓存获取失败");
        }

        return value;
    }

    /**
     * 设置缓存值
     *
     * @param key        缓存key
     * @param value      缓存值
     * @param timeNumber 时间值
     * @param unit       时间单位
     */
    public void set(String key, String value, long timeNumber, TimeUnit unit) {
        try {
            stringRedisTemplate.opsForValue().set(key, value, timeNumber, unit);
        } catch (Exception e) {
            log.warn("Redis缓存设置失败");
        }

        captchaCache.put(key, value);
    }

    /**
     * 删除缓存
     *
     * @param key 缓存key
     */
    public void delete(String key) {
        try {
            stringRedisTemplate.delete(key);
        } catch (Exception e) {
            log.warn("Redis缓存删除失败");
        }

        captchaCache.invalidate(key);
    }
}
