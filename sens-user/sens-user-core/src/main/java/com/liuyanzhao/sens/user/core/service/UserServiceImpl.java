package com.liuyanzhao.sens.user.core.service;

import com.liuyanzhao.sens.common.exception.InternalApiException;
import com.liuyanzhao.sens.common.vo.Response;
import com.liuyanzhao.sens.user.api.entity.User;
import com.liuyanzhao.sens.user.api.service.UserService;
import com.liuyanzhao.sens.user.core.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 言曌
 * @date 2019-08-07 08:45
 */
@RestController
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired(required = false)
    private UserMapper userMapper;

    @Override
    public Response<User> getUserById(Long id) {
        User user = userMapper.selectById(id);
        //如果用户被删除，
        if (user != null && user.getDeleteFlag() == 1) {
            user = null;
        }
        return Response.yes(user);
    }

    @Override
    public Response<User> getUserByUsername(String username) {
        User user = userMapper.findByUsername(username);
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
}
