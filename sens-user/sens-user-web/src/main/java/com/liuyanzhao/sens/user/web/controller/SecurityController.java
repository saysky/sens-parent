package com.liuyanzhao.sens.user.web.controller;

import com.liuyanzhao.sens.common.vo.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author liuyanzhao
 */
@Slf4j
@RestController
@RequestMapping("/uaa")
public class SecurityController {

    /**
     * 没有登录
     * @return
     */
    @RequestMapping(value = "/needLogin", method = RequestMethod.GET)
    public Response needLogin(){
        return Response.no(401, "您还未登录");
    }

}
