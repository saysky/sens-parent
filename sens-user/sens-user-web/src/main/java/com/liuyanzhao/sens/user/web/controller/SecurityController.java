package com.liuyanzhao.sens.user.web.controller;

import cn.hutool.http.HttpUtil;
import com.liuyanzhao.sens.common.vo.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author liuyanzhao
 */
@Slf4j
@RestController
@RequestMapping("/sens/common")
@Transactional
public class SecurityController {

    /**
     * 没有登录
     * @return
     */
    @RequestMapping(value = "/needLogin", method = RequestMethod.GET)
    public Response needLogin(){
        return Response.no(401, "您还未登录");
    }

    /**
     * 专用登录接口 方便测试
     * @param username
     * @param password
     * @param code 验证码
     * @param captchaId 图片验证码ID
     * @param loginUrl  可自定义登录接口地址
     * @return
     */
    @RequestMapping(value = "/swagger/login", method = RequestMethod.GET)
    public Response swaggerLogin(@RequestParam String username, @RequestParam String password,
                                       @RequestParam(required = false) String code,
                                       @RequestParam(required = false) String captchaId,
                                       @RequestParam(required = false, defaultValue = "http://127.0.0.1:8888/sens/login")String loginUrl){

        Map<String, Object> params = new HashMap<>(16);
        params.put("username", username);
        params.put("password", password);
        params.put("code", code);
        params.put("captchaId", captchaId);
        String result = HttpUtil.post(loginUrl, params);
        return Response.yes(result);
    }
}
