package com.liuyanzhao.sens.user.web.controller;

import com.liuyanzhao.sens.common.vo.Response;
import com.liuyanzhao.sens.user.web.util.IpInfoUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * IP接口
 *
 * @author liuyanzhao
 */
@Slf4j
@RestController
@RequestMapping("/common/ip")
@Transactional
public class IpInfoController {

    @Autowired
    private IpInfoUtil ipInfoUtil;

    /**
     * IP及天气相关信息
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public Response<Object> upload(HttpServletRequest request) {
        String result = ipInfoUtil.getIpWeatherInfo(ipInfoUtil.getIpAddr(request));
        return Response.yes(result);
    }
}