//package com.liuyanzhao.sens.user.web.config.security.validate;
//
//import cn.hutool.core.util.StrUtil;
//import cn.hutool.http.HttpUtil;
//import com.google.gson.Gson;
//import com.liuyanzhao.sens.common.IpInfoUtil;
//import com.liuyanzhao.sens.common.ResponseUtil;
//import com.liuyanzhao.sens.user.api.entity.Setting;
//import com.liuyanzhao.sens.user.api.service.SettingService;
//import com.liuyanzhao.sens.user.web.config.properties.CaptchaProperties;
//import com.liuyanzhao.sens.user.web.constant.SettingConstant;
//import com.liuyanzhao.sens.user.web.vo.VaptchaSetting;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.util.AntPathMatcher;
//import org.springframework.util.PathMatcher;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
///**
// * 手势
// * 图形验证码过滤器
// * @author liuyanzhao
// */
//@Slf4j
//@Configuration
//public class VaptchaValidateFilter extends OncePerRequestFilter {
//
//    @Autowired
//    private CaptchaProperties captchaProperties;
//
//    @Autowired
//    private SettingService settingService;
//
//    @Autowired
//    private IpInfoUtil ipInfoUtil;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
//
//        // 判断URL是否需要验证
//        Boolean flag = false;
//        String requestUrl = request.getRequestURI();
//        PathMatcher pathMatcher = new AntPathMatcher();
//        for(String url : captchaProperties.getVaptcha()){
//            if(pathMatcher.match(url, requestUrl)){
//                flag = true;
//                break;
//            }
//        }
//        if(flag){
//            String token = request.getParameter("token");
//
//            Setting setting = settingService.findById(SettingConstant.VAPTCHA_SETTING);
//            if(StrUtil.isBlank(setting.getValue())){
//                ResponseUtil.out(response, ResponseUtil.resultMap(false,500,"系统还未配置Vaptcha验证码，请联系管理员"));
//                return;
//            }
//            VaptchaSetting vs = new Gson().fromJson(setting.getValue(), VaptchaSetting.class);
//            // 验证vaptcha验证码
//            String params = "id=" + vs.getVid() + "&secretkey=" + vs.getSecretKey() + "&token=" + token
//                    + "&ip=" + ipInfoUtil.getIpAddr(request);
//            String result = HttpUtil.post(SettingConstant.VAPTCHA_URL, params);
//            if(!result.contains("\"success\":1")){
//                ResponseUtil.out(response, ResponseUtil.resultMap(false,500,"Vaptcha验证码验证失败"));
//                return;
//            }
//            // 验证成功 放行
//            chain.doFilter(request, response);
//            return;
//        }
//        // 无需验证 放行
//        chain.doFilter(request, response);
//    }
//}
