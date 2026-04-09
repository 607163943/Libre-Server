package com.libre.config;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.format.DateTimeFormatter;

@Configuration
public class TimeConfig {
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return builder -> {
            // 忽略未知属性，默认配置中前端传递接收实体不存在的属性jackson会报错
            builder.failOnUnknownProperties(false);
            // 设置旧版Date日期格式
            builder.simpleDateFormat("yyyy-MM-dd HH:mm:ss");
            // 顺便解决 Java 8 LocalDateTime 的问题
            builder.serializers(new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            builder.deserializers(new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        };
    }
}
