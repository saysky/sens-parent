package com.liuyanzhao.sens.user.core.service;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liuyanzhao.sens.common.vo.Response;
import com.liuyanzhao.sens.common.vo.SearchVo;
import com.liuyanzhao.sens.user.api.entity.User;
import com.liuyanzhao.sens.user.api.service.UserService;
import com.liuyanzhao.sens.user.core.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author 言曌
 * @date 2019-08-07 08:45
 */
@RestController
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public Response<User> getUserById(Long id) {
        User user = userMapper.selectById(id);
        return Response.yes(user);
    }


    @Override
    public Response<Boolean> deleteUserById(Integer id) {
        userMapper.deleteById(id);
        return Response.yes(true);
    }

    @Override
    public Response<Boolean> updateUser(User user) {
        userMapper.updateById(user);
        return Response.yes(true);
    }

    @Override
    public Response<Boolean> insertUser(User user) {
        userMapper.insert(user);
        return Response.yes();
    }

    @Override
    public Response<User> findByUsername(String username) {
        User user = userMapper.selectOne(
                new QueryWrapper<User>().eq("username", username)
        );
        return Response.yes(user);
    }

    @Override
    public Response<User> findByMobile(String mobile) {
        User user = userMapper.selectOne(
                new QueryWrapper<User>().eq("mobile", mobile)
        );
        return Response.yes(user);
    }

    @Override
    public Response<User> findByEmail(String email) {
        User user = userMapper.selectOne(
                new QueryWrapper<User>().eq("email", email)
        );
        return Response.yes(user);
    }

    @Override
    public Response<IPage<User>> findByCondition(User user, SearchVo searchVo, Page<User> page) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if(user != null) {
            if(user.getUsername() != null) {
                queryWrapper.like("username", user.getUsername());
            }
            if(user.getNickname() != null) {
                queryWrapper.like("nickname", user.getNickname());
            }
            if(user.getStatus() != null) {
                queryWrapper.eq("status", user.getStatus());
            }
            if(user.getType() != null) {
                queryWrapper.eq("type", user.getType());
            }
            if(user.getSex() != null) {
                queryWrapper.eq("sex", user.getSex());
            }
            if(user.getEmail() != null) {
                queryWrapper.eq("email", user.getEmail());
            }
            if(user.getMobile() != null) {
                queryWrapper.eq("mobile", user.getMobile());
            }
        }
        if(searchVo != null) {
            if(searchVo.getStartDate() != null && searchVo.getEndDate() != null) {
                Date start = DateUtil.parse(searchVo.getStartDate());
                Date end = DateUtil.parse(searchVo.getEndDate());
                queryWrapper.between("create_time", start, end);
            }
        }
        IPage<User> userIPage = userMapper.selectPage(page, queryWrapper);
        return Response.yes(userIPage);
    }
}
