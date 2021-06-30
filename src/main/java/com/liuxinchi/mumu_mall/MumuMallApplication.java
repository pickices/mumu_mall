package com.liuxinchi.mumu_mall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableCaching
public class MumuMallApplication {

    public static void main(String[] args) {
        SpringApplication.run(MumuMallApplication.class, args);
    }

}
