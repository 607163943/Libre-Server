package com.libre;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.libre.mapper")
@SpringBootApplication
public class LibreServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(LibreServerApplication.class, args);
    }

}
