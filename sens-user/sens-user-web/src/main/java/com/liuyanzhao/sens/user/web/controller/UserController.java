package com.liuyanzhao.sens.user.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liuyanzhao.sens.common.util.PageUtil;
import com.liuyanzhao.sens.common.vo.PageVo;
import com.liuyanzhao.sens.common.vo.SearchVo;
import com.liuyanzhao.sens.user.api.dto.UserCondition;
import com.liuyanzhao.sens.user.api.entity.User;
import com.liuyanzhao.sens.user.api.service.UserService;
import com.liuyanzhao.sens.common.vo.Response;
import com.liuyanzhao.sens.user.web.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 言曌
 * @date 2019-08-09 16:32
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityUtil securityUtil;

    /**
     * 获取当前登录用户接口
     * @return
     */
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public Response<User> getUserInfo(){
        User u = securityUtil.getCurrUser();
        u.setPassword(null);
        return Response.yes(u);
    }

    /**
     * 用户详细信息
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Response<User> getUserById(@PathVariable("id") Long id) {
        Response<User> response = userService.getUserById(id);
        if (response.getData() == null) {
            return Response.no("用户不存在");
        }
        return response;
    }


    /**
     * 用户详细信息
     *
     * @param username
     * @return
     */
    @GetMapping("/username/{username}")
    public Response<User> getUserByName(@PathVariable("username") String username) {
        Response<User> response = userService.findByUsername(username);
        if (response.getData() == null) {
            return Response.no("用户不存在");
        }
        return response;
    }

    /**
     * 用户详细信息
     *
     * @param
     * @return
     */
    @GetMapping
    public Response<Page<User>> findAll(@ModelAttribute User user,
                                        @ModelAttribute SearchVo searchVo,
                                        @ModelAttribute PageVo pageVo) {
        UserCondition userCondition = new UserCondition( user, searchVo, PageUtil.initMpPage(pageVo));
        return userService.findByCondition(userCondition);
    }
}
