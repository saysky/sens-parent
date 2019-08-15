package com.liuyanzhao.sens.user.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@EnableFeignClients(basePackages = "com.liuyanzhao.sens")
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableEurekaClient
@EnableCircuitBreaker
//@SpringCloudApplication
@ComponentScan("com.liuyanzhao.sens")
public class SensUserWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(SensUserWebApplication.class, args);
    }

}
