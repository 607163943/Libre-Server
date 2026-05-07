package com.libre;

import org.dromara.x.file.storage.spring.EnableFileStorage;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableFileStorage
@EnableScheduling
@MapperScan("com.libre.mapper")
@SpringBootApplication
public class LibreServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(LibreServerApplication.class, args);
    }

}
