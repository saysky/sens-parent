package com.liuyanzhao.sens.common.config;

import com.liuyanzhao.sens.common.vo.Response;
import com.liuyanzhao.sens.common.exception.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 言曌
 * @date 2019-08-09 16:40
 */

@RestController
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @Resource
    private MessageSource messageSource;

    /**
     * 请求参数不合法
     * @param request
     * @param response
     * @param e
     * @return
     */
    @ExceptionHandler({org.springframework.web.bind.MissingServletRequestParameterException.class})
    @ResponseBody
    public Response processRequestParameterException(HttpServletRequest request,
                                                     HttpServletResponse response,
                                                     MissingServletRequestParameterException e) {

        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType("application/json;charset=UTF-8");
        Response result = new Response();
        result.setCode(400);
        result.setMessage("BAD_REQUEST");
        return result;
    }

    /**
     * 默认异常
     * @param response
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Response processDefaultException(HttpServletResponse response,
                                            Exception e) {
        log.error("Server exeption", e);
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setContentType("application/json;charset=UTF-8");
        Response result = new Response();
        result.setCode(500);
        result.setMessage("INTERNAL_SERVER_ERROR");
        return result;
    }


    /**
     * 统一异常处理
     * @param response
     * @param e
     * @return
     */
    @ExceptionHandler(ApiException.class)
    @ResponseBody
    public Response processApiException(HttpServletResponse response,
                                        ApiException e) {
        Response result = new Response();
        response.setStatus(e.getCode());
        response.setContentType("application/json;charset=UTF-8");
        result.setCode(e.getCode());
        result.setMessage(e.getMessage());
        log.error("Known exception from service consumer.", e.getMessage(), e);
        return result;
    }
}
