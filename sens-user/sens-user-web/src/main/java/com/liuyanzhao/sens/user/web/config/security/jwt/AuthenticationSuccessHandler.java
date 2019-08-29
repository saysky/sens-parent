package com.liuyanzhao.sens.user.web.config.security.jwt;

import cn.hutool.core.util.StrUtil;
import com.google.gson.Gson;
import com.liuyanzhao.sens.common.util.ResponseUtil;
import com.liuyanzhao.sens.common.annotation.SystemLog;
import com.liuyanzhao.sens.common.enums.LogType;
import com.liuyanzhao.sens.user.web.config.properties.TokenProperties;
import com.liuyanzhao.sens.user.web.constant.SecurityConstant;
import com.liuyanzhao.sens.user.web.util.IpInfoUtil;
import com.liuyanzhao.sens.user.web.vo.TokenUser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 登录成功处理类
 *
 * @author liuyanzhao
 */
@Slf4j
@Component
public class AuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    private TokenProperties tokenProperties;

    @Autowired
    private IpInfoUtil ipInfoUtil;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    @SystemLog(description = "登录系统", type = LogType.LOGIN)
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        //用户选择保存登录状态几天
        String saveLogin = request.getParameter(SecurityConstant.SAVE_LOGIN);
        Boolean saved = false;
        if (StrUtil.isNotBlank(saveLogin) && Boolean.valueOf(saveLogin)) {
            saved = true;
            if (!tokenProperties.getRedis()) {
                tokenProperties.setTokenExpireTime(tokenProperties.getSaveLoginTime() * 60 * 24);
            }
        }
        String username = ((UserDetails) authentication.getPrincipal()).getUsername();
        List<GrantedAuthority> authorities = (List<GrantedAuthority>) ((UserDetails) authentication.getPrincipal()).getAuthorities();
        List<String> list = new ArrayList<>();
        for (GrantedAuthority g : authorities) {
            list.add(g.getAuthority());
        }
        ipInfoUtil.getUrl(request);
        // 登陆成功生成token
        String token;
        if (tokenProperties.getRedis()) {
            // redis
            token = UUID.randomUUID().toString().replace("-", "");
            TokenUser user = new TokenUser(username, list, saved);
            // 不缓存权限
            if (!tokenProperties.getStorePerms()) {
                user.setPermissions(null);
            }
            // 单设备登录 之前的token失效
            if (tokenProperties.getSdl()) {
                String oldToken = redisTemplate.opsForValue().get(SecurityConstant.USER_TOKEN + username);
                if (StrUtil.isNotBlank(oldToken)) {
                    redisTemplate.delete(SecurityConstant.TOKEN_PRE + oldToken);
                }
            }
            if (saved) {
                redisTemplate.opsForValue().set(SecurityConstant.USER_TOKEN + username, token, tokenProperties.getSaveLoginTime(), TimeUnit.DAYS);
                redisTemplate.opsForValue().set(SecurityConstant.TOKEN_PRE + token, new Gson().toJson(user), tokenProperties.getSaveLoginTime(), TimeUnit.DAYS);
            } else {
                redisTemplate.opsForValue().set(SecurityConstant.USER_TOKEN + username, token, tokenProperties.getTokenExpireTime(), TimeUnit.MINUTES);
                redisTemplate.opsForValue().set(SecurityConstant.TOKEN_PRE + token, new Gson().toJson(user), tokenProperties.getTokenExpireTime(), TimeUnit.MINUTES);
            }
        } else {
            // 不缓存权限
            if (!tokenProperties.getStorePerms()) {
                list = null;
            }
            // jwt
            token = SecurityConstant.TOKEN_SPLIT + Jwts.builder()
                    //主题 放入用户名
                    .setSubject(username)
                    //自定义属性 放入用户拥有请求权限
                    .claim(SecurityConstant.AUTHORITIES, new Gson().toJson(list))
                    //失效时间
                    .setExpiration(new Date(System.currentTimeMillis() + tokenProperties.getTokenExpireTime() * 60 * 1000))
                    //签名算法和密钥
                    .signWith(SignatureAlgorithm.HS512, SecurityConstant.JWT_SIGN_KEY)
                    .compact();
        }

        //cookie里放一份 accessToken
        addCookie(response, token);

        ResponseUtil.out(response, ResponseUtil.resultMap(true, 200, "登录成功", token));
    }

    private void addCookie(HttpServletResponse response, String token) {
        //将token写入cookie
        Cookie cookie = new Cookie(SecurityConstant.HEADER, token);
        cookie.setMaxAge(tokenProperties.getTokenExpireTime() * 60);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

}
