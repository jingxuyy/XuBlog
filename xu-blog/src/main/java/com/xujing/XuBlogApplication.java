package com.xujing;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author 86136
 */
@SpringBootApplication
@MapperScan("com.xujing.mapper")
@EnableScheduling
@EnableSwagger2
public class XuBlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(XuBlogApplication.class, args);
    }
}
