package com.liuyanzhao.sens.user.core;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * 用户中心生产者启动类
 * @author liuyanzhao
 */
@EnableEurekaClient
@SpringBootApplication
@MapperScan("com.liuyanzhao.sens.user.core.mapper")
public class SensUserCoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(SensUserCoreApplication.class, args);
    }

}
