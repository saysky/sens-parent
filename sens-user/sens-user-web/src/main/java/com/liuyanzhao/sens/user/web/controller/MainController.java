package com.liuyanzhao.sens.user.web.controller;

import com.liuyanzhao.sens.user.api.entity.User;
import com.liuyanzhao.sens.user.api.service.UserService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 言曌
 * @date 2019-08-08 00:08
 */

@RestController
public class MainController {

    @Autowired
    private UserService userService;

    @GetMapping("/hello")
    @HystrixCommand(fallbackMethod = "fallback")
    public String hello() {
        //调用了服务，令该服务不可用，测试服务降级
        userService.findById("1");
        return "Hello, I will always be there.";
    }


    private String fallback() {
        return "太拥挤了，请稍后再试...";
    }



}
