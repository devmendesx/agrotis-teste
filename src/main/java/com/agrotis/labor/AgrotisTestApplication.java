package com.agrotis.labor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class AgrotisTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(AgrotisTestApplication.class, args);
    }

}
