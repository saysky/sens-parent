package com.liuyanzhao.sens.user.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * 用户中心生产者启动类
 * @author liuyanzhao
 */
@EnableEurekaClient
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class SensUserCoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(SensUserCoreApplication.class, args);
    }

}
