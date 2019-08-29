package com.liuyanzhao.sens.user.web.controller;

import com.liuyanzhao.sens.common.vo.Response;
import com.liuyanzhao.sens.user.web.util.CreateVerifyCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author liuyanzhao
 */
@RequestMapping("/common/captcha")
@RestController
@Slf4j
public class CaptchaController {


    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 初始化验证码
     *
     * @return
     */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public Response<Object> initCaptcha() {

        String captchaId = UUID.randomUUID().toString().replace("-", "");
        String code = new CreateVerifyCode().randomStr(4);
        // 缓存验证码
        redisTemplate.opsForValue().set(captchaId, code, 2L, TimeUnit.MINUTES);
        return Response.yes(captchaId);
    }

    /**
     * 根据验证码ID获取图片
     *
     * @param captchaId
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/draw/{captchaId}", method = RequestMethod.GET)
    public void drawCaptcha(@PathVariable("captchaId") String captchaId,
                            HttpServletResponse response) throws IOException {

        // 得到验证码 生成指定验证码
        String code = redisTemplate.opsForValue().get(captchaId);
        CreateVerifyCode vCode = new CreateVerifyCode(116, 36, 4, 10, code);
        response.setContentType("image/png");
        vCode.write(response.getOutputStream());
    }

}
